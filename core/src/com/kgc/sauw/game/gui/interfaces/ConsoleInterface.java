package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.EditText;
import com.kgc.sauw.core.gui.elements.MultilineTextView;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.skins.Skins;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.core.graphic.Graphic.INTERFACE_CAMERA;
import static com.kgc.sauw.core.gui.Interfaces.HUD;

@RegistryMetadata(package_ = "sauw", id = "console")
public class ConsoleInterface extends Interface {
    public Button sendCommandButton;
    public Button nextCommand;
    public Button prevCommand;
    private int currCom = -1;
    public EditText input;
    String inputTxt = "";
    public Context cx;
    public Scriptable sc;
    private MultilineTextView log;

    public ConsoleInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/ConsoleInterface.xml"), this);

        input = (EditText) getElement("edittext.CommandInput");
        sendCommandButton = (Button) getElement("button.sendCommandButton");

        nextCommand = (Button) getElement("button.prevCommand");
        prevCommand = (Button) getElement("button.nextCommand");

        log = (MultilineTextView) getElement("text.log");
        log.setBackground(Skins.round_down);


        prevCommand.setIcon(Resource.getTexture("interface/button_left_0.png"));
        nextCommand.setIcon(Resource.getTexture("interface/button_right_0.png"));

        /*nextCommand.addEventListener(() -> {
            currCom++;
            if (currCom >= ModAPI.Console.inputs.size()) {
                currCom = ModAPI.Console.inputs.size() - 1;
            }
            if (currCom != -1) {
                input.setText(ModAPI.Console.input(currCom));
            } else {
                input.setText(inputTxt);
            }
        });
        prevCommand.addEventListener(() -> {
            currCom--;
            if (currCom < 0) {
                currCom = -1;
            }
            if (currCom != -1) {
                input.setText(ModAPI.Console.input(currCom));
            } else {
                input.setText(inputTxt);
            }
        });*/
        cx = Context.enter();
        cx.setOptimizationLevel(-1);
        try {
            sc = cx.initStandardObjects();
            ScriptableObject.putProperty(sc, "Player", PLAYER);
            ScriptableObject.putProperty(sc, "GI", HUD);
            ScriptableObject.putProperty(sc, "World", getWorld());
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        } finally {
            Context.exit();
        }
        updateElementsList();
    }

    @Override
    public void tick() {
        input.setTextColor(HUD.r, HUD.g, HUD.b);
        if (currCom == -1) {
            inputTxt = input.getInput();
        }
        if (sendCommandButton.isTouched() && input.getInput() != null && !input.getInput().equals("")) {
            cx = Context.enter();
            cx.setOptimizationLevel(-1);
            try {
                cx.evaluateString(sc, Gdx.files.internal("js/commands.js").readString() + input.getInput(), "Command", 1, null);
            } catch (Exception e) {
                log.setText(ExceptionUtils.getStackTrace(e));
                Gdx.app.log("error", e.toString());
            } finally {
                Context.exit();
            }
            //ModAPI.Console.inputs.add(input.getText());
            input.clear();
        }
        if (input.y == 0) input.y = (int) (y + BLOCK_SIZE / 2f);
        sendCommandButton.y = input.y;
        input.update(INTERFACE_CAMERA);
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void preRender() {

    }
}
