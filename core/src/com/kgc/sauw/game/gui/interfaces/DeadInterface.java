package com.kgc.sauw.game.gui.interfaces;

import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.utils.languages.Languages;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_WIDTH;

public class DeadInterface extends Interface {
    Button respawn;

    public DeadInterface() {
        super("DEAD_INTERFACE");
        setHeaderText("");

        respawn = new Button("DEAD_INTERFACE_RESPAWN_BUTTON", (int) (SCREEN_WIDTH / 16 * 5), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 5), (int) (SCREEN_WIDTH / 16 * 6), (int) (SCREEN_WIDTH / 16));
        respawn.setText(Languages.getString("respawn"));
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
