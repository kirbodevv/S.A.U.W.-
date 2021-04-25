package com.kgc.sauw.UI.Interfaces;

import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Interface;

import static com.kgc.sauw.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.map.World.WORLD;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class PauseInterface extends Interface {
    Button saveWorldButton;
    Button resumeButton;

    public PauseInterface() {
        super("PAUSE_INTERFACE");
        setHeaderText("").isBlockInterface(false);

        resumeButton = new Button("PAUSE_INTERFACE_RESUME_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 4), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        resumeButton.setText(LANGUAGES.getString("resume"));
        resumeButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });
        saveWorldButton = new Button("PAUSE_INTERFACE_SAVE_WORLD_BUTTON", (int) (SCREEN_WIDTH / 16), (int) (resumeButton.Y - SCREEN_WIDTH / 16), (int) (SCREEN_WIDTH / 16 * 4), (int) SCREEN_WIDTH / 16);
        saveWorldButton.setText(LANGUAGES.getString("save"));
        saveWorldButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                WORLD.save();
            }
        });
        exitButton.hide(true);
        Elements.add(resumeButton);
        Elements.add(saveWorldButton);

        updateElementsList();
    }
}
