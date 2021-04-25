package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.EditText;
import com.kgc.sauw.UI.Interface;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

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

    public ConsoleInterface() {
        super("CONSOLE_INTERFACE");
        setHeaderText(LANGUAGES.getString("console")).isBlockInterface(false);
        int inW = (int) (width - SCREEN_WIDTH / 8 - SCREEN_WIDTH / 32);
        input = new EditText((int) (SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), inW, (int) SCREEN_WIDTH / 16, INPUT_MULTIPLEXER);
        log_bg = TEXTURES.generateTexture((width - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 16) / (SCREEN_WIDTH / 16), ((heigth - SCREEN_WIDTH / 16) - (SCREEN_WIDTH / 8)) / (SCREEN_WIDTH / 16), false);
        nextCommand = new Button("CONSOLE_INTERFACE_NEXT_COMMAND_BUTTON", input.X + input.width, (int) (y + SCREEN_WIDTH / 16), (int) (SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), TEXTURES.button_up_0, TEXTURES.button_up_1);
        prevCommand = new Button("CONSOLE_INTERFACE_NEXT_COMMAND_BUTTON", input.X + input.width, (int) (y + SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), TEXTURES.button_down_0, TEXTURES.button_down_1);
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
        sendCommandButton = new Button("CONSOLE_INTERFACE_SEND_COMMAND_BUTTON", input.X + input.width + (int) (SCREEN_WIDTH / 32), input.Y, (int) SCREEN_WIDTH / 16, (int) SCREEN_WIDTH / 16, TEXTURES.button_right_0, TEXTURES.button_right_1);
        Elements.add(sendCommandButton);
        Elements.add(nextCommand);
        Elements.add(prevCommand);
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
        input.input = "give(1, 1, 0);";
        updateElementsList();
    }

    @Override
    public void preRender() {
        BATCH.draw(log_bg, x + SCREEN_WIDTH / 32, y + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 16, width - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 16, (heigth - SCREEN_WIDTH / 16) - (SCREEN_WIDTH / 8));
    }

    @Override
    public void postRender() {
        GAME_INTERFACE.Log.drawWrapped(BATCH, GAME_INTERFACE.logText, x + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 64, y + heigth - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64, SCREEN_WIDTH - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32);
        input.render(BATCH, INTERFACE_CAMERA);
    }

    @Override
    public void onClose() {
        super.onClose();
        //input.hide(true);
    }

    @Override
    public void onOpen() {
        super.onOpen();
        //input.hide(false);
    }

    @Override
    public void tick() {
        GAME_INTERFACE.Log.setColor(GAME_INTERFACE.R / 255f, GAME_INTERFACE.G / 255f, GAME_INTERFACE.B / 255f, 1);
        input.setTextColor(GAME_INTERFACE.R / 255f, GAME_INTERFACE.G / 255f, GAME_INTERFACE.B / 255f);
        if (currCom == -1) {
            inputTxt = input.input;
        }
        if (sendCommandButton.isTouched() && input.input != null && input.input != "") {
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
        //input.Y = input.isKeyboardOpen ? game.keyboardHeight : (int) (Interface.y + SCREEN_WIDTH / 32);
        if (input.Y == 0) input.Y = (int) (y + SCREEN_WIDTH / 32);
        sendCommandButton.Y = input.Y;
        prevCommand.Y = input.Y;
        nextCommand.Y = prevCommand.Y + prevCommand.height;
        input.update(INTERFACE_CAMERA);
    }
}
