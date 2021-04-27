package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.EditText;
import com.kgc.sauw.UI.Elements.Image;
import com.kgc.sauw.UI.Elements.Layout;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Units;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import sun.applet.Main;

import static com.kgc.sauw.Input.INPUT_MULTIPLEXER;
import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.utils.Languages.LANGUAGES;
import static com.kgc.sauw.game.SAUW.MOD_API;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.map.World.WORLD;

public class ConsoleInterface extends Interface {
    public Button sendCommandButton;
    public Button nextCommand;
    public Button prevCommand;
    private int currCom = -1;
    public EditText input;
    Texture log_bg;
    String inputTxt = "";
    public Context cx;
    public Scriptable sc;
    private Image log;

    public ConsoleInterface() {
        super("CONSOLE_INTERFACE");
        createFromXml(Gdx.files.internal("xml/ConsoleInterface.xml").readString());

        log_bg = Textures.generateTexture((width - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 16) / (SCREEN_WIDTH / 16), Units.fromStringToPx("77%H") / BLOCK_SIZE, false);
        input = (EditText) getElement("CommandInput");
        sendCommandButton = (Button) getElement("sendCommandButton");
        log = ((Image) getElement("log_bg"));
        log.setImg(log_bg);

        nextCommand = (Button) getElement("nextCommand");
        prevCommand = (Button) getElement("prevCommand");

        nextCommand.setTextures(TEXTURES.button_right_0, TEXTURES.button_right_1);
        prevCommand.setTextures(TEXTURES.button_left_0, TEXTURES.button_left_1);

        nextCommand.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                currCom++;
                if (currCom >= MOD_API.Console.inputs.size()) {
                    currCom = MOD_API.Console.inputs.size() - 1;
                }
                if (currCom != -1) {
                    input.input = MOD_API.Console.input(currCom);
                } else {
                    input.input = inputTxt;
                }
            }
        });
        prevCommand.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                currCom--;
                if (currCom < 0) {
                    currCom = -1;
                }
                if (currCom != -1) {
                    input.input = MOD_API.Console.input(currCom);
                } else {
                    input.input = inputTxt;
                }
            }
        });
        GAME_INTERFACE.Log.setColor(Color.BLACK);
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try {
            sc = cx.initStandardObjects();
            ScriptableObject.putProperty(sc, "Player", PLAYER);
            ScriptableObject.putProperty(sc, "GI", GAME_INTERFACE);
            ScriptableObject.putProperty(sc, "World", WORLD);
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        } finally {
            Context.exit();
        }
        updateElementsList();
    }

    @Override
    public void postRender() {
        GAME_INTERFACE.Log.drawWrapped(BATCH, GAME_INTERFACE.logText, x + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 64, log.Y + log.height - BLOCK_SIZE / 4f, SCREEN_WIDTH - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32);
    }

    @Override
    public void tick() {
        GAME_INTERFACE.Log.setColor(GAME_INTERFACE.R / 255f, GAME_INTERFACE.G / 255f, GAME_INTERFACE.B / 255f, 1);
        input.setTextColor(GAME_INTERFACE.R / 255f, GAME_INTERFACE.G / 255f, GAME_INTERFACE.B / 255f);
        if (currCom == -1) {
            inputTxt = input.input;
        }
        if (sendCommandButton.isTouched() && input.input != null && !input.input.equals("")) {
            cx = Context.enter();
            cx.setOptimizationLevel(-1);
            try {
                cx.evaluateString(sc, Gdx.files.internal("js/commands.js").readString() + input.input, "Command", 1, null);
            } catch (Exception e) {
                GAME_INTERFACE.consolePrint(e.toString());
                Gdx.app.log("error", e.toString());
            } finally {
                Context.exit();
            }
            MOD_API.Console.inputs.add(input.input);
            input.clear();
        }
        if (input.Y == 0) input.Y = (int) (y + SCREEN_WIDTH / 32);
        sendCommandButton.Y = input.Y;
        input.update(INTERFACE_CAMERA);
    }
}
