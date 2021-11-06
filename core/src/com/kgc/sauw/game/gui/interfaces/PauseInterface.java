package com.kgc.sauw.game.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.InterfaceUtils;
import com.kgc.sauw.core.gui.OnClickListener;
import com.kgc.sauw.core.gui.elements.Button;

public class PauseInterface extends Interface {
    Button saveWorldButton;
    Button resumeButton;

    public PauseInterface() {
        InterfaceUtils.createFromXml(Gdx.files.internal("xml/PauseInterface.xml"), this);
        resumeButton = (Button) getElement("button.resume");//new Button("PAUSE_INTERFACE_RESUME_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 4), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        resumeButton.setDefaultText();
        resumeButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                close();
            }
        });
        saveWorldButton = (Button) getElement("button.save_world"); //new Button("PAUSE_INTERFACE_SAVE_WORLD_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (resumeButton.Y - SCREEN_WIDTH / 16), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        saveWorldButton.setDefaultText();
        saveWorldButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                Environment.save();
            }
        });

        closeInterfaceButton.hide(true);
        /*Elements.add(resumeButton);
        Elements.add(saveWorldButton);*/

        updateElementsList();
    }

    @Override
    public void tick() {

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

    @Override
    public void postRender() {

    }
}
