package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.EditText;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.UI.InterfaceEvents;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.environment.Environment.LANGUAGES;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.graphic.Graphic.BATCH;

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
        super(InterfaceSizes.FULL, GAME_INTERFACE);
        setHeaderText(LANGUAGES.getString("console")).isBlockInterface(false);
        setInterfaceEvents(new InterfaceEvents() {
            public void initialize() {

                int inW = (int) (Interface.width - SCREEN_WIDTH / 8 - SCREEN_WIDTH / 32);
                input = new EditText((int) (Interface.x + SCREEN_WIDTH / 32), (int) (Interface.y + SCREEN_WIDTH / 32), inW, (int) SCREEN_WIDTH / 16, INTERFACE_CAMERA, GAME_INTERFACE.multiplexer);
                input.hide(true);
                log_bg = TEXTURES.generateTexture((Interface.width - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 16) / (SCREEN_WIDTH / 16), ((Interface.heigth - SCREEN_WIDTH / 16) - (SCREEN_WIDTH / 8)) / (SCREEN_WIDTH / 16), false);
                nextCommand = new Button("", input.X + input.w, (int) (Interface.y + SCREEN_WIDTH / 16), (int) (SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), TEXTURES.button_up_0, TEXTURES.button_up_1);
                prevCommand = new Button("", input.X + input.w, (int) (Interface.y + SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), (int) (SCREEN_WIDTH / 32), TEXTURES.button_down_0, TEXTURES.button_down_1);
               /* nextCommand.setEventListener(new Button.EventListener() {
                    @Override
                    public void onClick() {
                        currCom++;
                        if (currCom >= ModAPI.Console.inputs.size()) {
                            currCom = ModAPI.Console.inputs.size() - 1;
                        }
                        if (currCom != -1) {
                            input.input = ModAPI.Console.input(currCom);
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
                            input.input = ModAPI.Console.input(currCom);
                        } else {
                            input.input = inputTxt;
                        }
                    }
                });*/
                sendCommandButton = new Button("SendButton", input.X + input.w + (int) (SCREEN_WIDTH / 32), input.Y, (int) SCREEN_WIDTH / 16, (int) SCREEN_WIDTH / 16, TEXTURES.button_right_0, TEXTURES.button_right_1);
                this.Interface.buttons.add(sendCommandButton);
                this.Interface.buttons.add(nextCommand);
                this.Interface.buttons.add(prevCommand);
                GAME_INTERFACE.Log.setColor(Color.BLACK);
                cx = Context.enter();
                cx.setOptimizationLevel(-1);
                try {
                    sc = cx.initStandardObjects();
                    //ScriptableObject.putProperty(sc, "Player", pl);
                    ScriptableObject.putProperty(sc, "GI", GAME_INTERFACE);
                    //ScriptableObject.putProperty(sc, "World", w);
                } catch (Exception e) {
                    Gdx.app.log("error", e.toString());
                } finally {
                    Context.exit();
                }
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
                    //ModAPI.Console.inputs.add(input.input);
                    input.clear();
                }
                //input.Y = input.isKeyboardOpen ? game.keyboardHeight : (int) (Interface.y + SCREEN_WIDTH / 32);
                if (input.Y == 0) input.Y = (int) (Interface.y + SCREEN_WIDTH / 32);
                sendCommandButton.Y = input.Y;
                prevCommand.Y = input.Y;
                nextCommand.Y = prevCommand.Y + prevCommand.height;
                input.update();
            }

            @Override
            public void onOpen() {
                input.hide(false);
            }

            @Override
            public void onClose() {
                input.hide(true);
            }

            @Override
            public void renderBefore() {
                BATCH.draw(log_bg, Interface.x + SCREEN_WIDTH / 32, Interface.y + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 16, Interface.width - SCREEN_WIDTH / 8 + SCREEN_WIDTH / 16, (Interface.heigth - SCREEN_WIDTH / 16) - (SCREEN_WIDTH / 8));
            }

            @Override
            public void render() {
                GAME_INTERFACE.Log.drawWrapped(BATCH, GAME_INTERFACE.logText, Interface.x + SCREEN_WIDTH / 32 + SCREEN_WIDTH / 64, Interface.y + Interface.heigth - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32 - SCREEN_WIDTH / 64, SCREEN_WIDTH - SCREEN_WIDTH / 16 - SCREEN_WIDTH / 32);
                input.render(BATCH);
            }
        });
    }
}
