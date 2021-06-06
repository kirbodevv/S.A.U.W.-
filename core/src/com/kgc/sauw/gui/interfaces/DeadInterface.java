package com.kgc.sauw.gui.interfaces;

import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.Interface;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_WIDTH;

public class DeadInterface extends Interface {
    Button respawn;

    public DeadInterface() {
        super("DEAD_INTERFACE");
        setHeaderText("").isBlockInterface(false);

        respawn = new Button("DEAD_INTERFACE_RESPAWN_BUTTON", (int) (SCREEN_WIDTH / 16 * 5), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 5), (int) (SCREEN_WIDTH / 16 * 6), (int) (SCREEN_WIDTH / 16));
        respawn.setText(LANGUAGES.getString("respawn"));
        respawn.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                PLAYER.randomSpawn();
                close();
            }
        });
        closeInterfaceButton.hide(true);
        elements.add(respawn);

        updateElementsList();
    }
}
