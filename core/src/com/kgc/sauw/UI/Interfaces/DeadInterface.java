package com.kgc.sauw.ui.interfaces;

import com.kgc.sauw.ui.elements.Button;
import com.kgc.sauw.ui.Interface;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.utils.Languages.LANGUAGES;
import static com.kgc.sauw.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;

public class DeadInterface extends Interface {
    Button respawn;

    public DeadInterface() {
        super("DEAD_INTERFACE");
        setHeaderText("").isBlockInterface(false);

        respawn = new Button("DEAD_INTERFACE_RESPAWN_BUTTON", (int) (SCREEN_WIDTH / 16 * 5), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 5), (int) (SCREEN_WIDTH / 16 * 6), (int) (SCREEN_WIDTH / 16));
        //respawn.setTextColor(Color.BLACK);
        respawn.setText(LANGUAGES.getString("respawn"));
        respawn.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                PLAYER.randomSpawn();
                close();
            }
        });
        closeInterfaceButton.hide(true);
        Elements.add(respawn);

        updateElementsList();
    }
}
