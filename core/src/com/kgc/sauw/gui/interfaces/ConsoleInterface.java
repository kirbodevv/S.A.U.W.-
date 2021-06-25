package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.EditText;
import com.kgc.sauw.core.gui.elements.Image;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.resource.TextureGenerator;
import com.kgc.sauw.core.utils.Units;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.game.SAUW.MOD_API;
import static com.kgc.sauw.core.graphic.Graphic.*;
import static com.kgc.sauw.gui.Interfaces.HUD;
import static com.kgc.sauw.core.map.World.WORLD;

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

    public ConsoleInterface() {
        super("CONSOLE_INTERFACE");
        createFromXml(Gdx.files.internal("xml/ConsoleInterface.xml"));

        input = (EditText) getElement("CommandInput");
        sendCommandButton = (Button) getElement("sendCommandButton");
        Layout log = ((Layout) getElement("log_bg"));
        log.setSizeInBlocks(15f, 6f);
        log.setStandardBackground(false);

        nextCommand = (Button) getElement("nextCommand");
        prevCommand = (Button) getElement("prevCommand");

        prevCommand.setIcon(TEXTURES.icon_left);
        nextCommand.setIcon(TEXTURES.icon_right);

        nextCommand.addEventListener(new Button.EventListener() {
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
        prevCommand.addEventListener(new Button.EventListener() {
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
        HUD.Log.setColor(Color.BLACK);
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try {
            sc = cx.initStandardObjects();
            ScriptableObject.putProperty(sc, "Player", PLAYER);
            ScriptableObject.putProperty(sc, "GI", HUD);
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
        //GAME_INTERFACE.Log.draw(BATCH, GAME_INTERFACE.logText, x + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 64, log.Y + log.height - BLOCK_SIZE / 4f, SCREEN_WIDTH - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32);
    }

    @Override
    public void tick() {
        HUD.Log.setColor(HUD.R / 255f, HUD.G / 255f, HUD.B / 255f, 1);
        input.setTextColor(HUD.R / 255f, HUD.G / 255f, HUD.B / 255f);
        if (currCom == -1) {
            inputTxt = input.input;
        }
        if (sendCommandButton.isTouched() && input.input != null && !input.input.equals("")) {
            cx = Context.enter();
            cx.setOptimizationLevel(-1);
            try {
                cx.evaluateString(sc, Gdx.files.internal("js/commands.js").readString() + input.input, "Command", 1, null);
            } catch (Exception e) {
                HUD.consolePrint(e.toString());
                Gdx.app.log("error", e.toString());
            } finally {
                Context.exit();
            }
            MOD_API.Console.inputs.add(input.input);
            input.clear();
        }
        if (input.y == 0) input.y = (int) (y + BLOCK_SIZE / 2f);
        sendCommandButton.y = input.y;
        input.update(INTERFACE_CAMERA);
    }
}
