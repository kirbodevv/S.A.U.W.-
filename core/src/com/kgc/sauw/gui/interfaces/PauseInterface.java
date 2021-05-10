package com.kgc.sauw.gui.interfaces;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.gui.elements.Button;
import com.kgc.sauw.gui.Interface;

import static com.kgc.sauw.map.World.WORLD;

public class PauseInterface extends Interface {
    Button saveWorldButton;
    Button resumeButton;

    public PauseInterface() {
        super("PAUSE_INTERFACE");
        //setHeaderText("").isBlockInterface(false);
        createFromXml(Gdx.files.internal("xml/PauseInterface.xml"));
        resumeButton = (Button) getElement("PAUSE_INTERFACE_RESUME_BUTTON");//new Button("PAUSE_INTERFACE_RESUME_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 4), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        //resumeButton.setText(LANGUAGES.getString("resume"));
        resumeButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });
        saveWorldButton = (Button) getElement("PAUSE_INTERFACE_SAVE_WORLD_BUTTON"); //new Button("PAUSE_INTERFACE_SAVE_WORLD_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (resumeButton.Y - SCREEN_WIDTH / 16), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        //saveWorldButton.setText(LANGUAGES.getString("save"));
        saveWorldButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                WORLD.save();
            }
        });

        closeInterfaceButton.hide(true);
        /*Elements.add(resumeButton);
        Elements.add(saveWorldButton);*/

        updateElementsList();
    }
}
