package com.kgc.sauw.game.gui.interfaces;

import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.gui.OnClickListener;
import com.kgc.sauw.core.gui.elements.Button;
import com.jvmfrog.curve.registry.RegistryMetadata;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.core.graphic.Graphic.SCREEN_WIDTH;
@RegistryMetadata("sauw:death")
public class DeathInterface extends Interface {
    Button respawn;

    public DeathInterface() {
        id = "sauw.interface.dead";
        actionBar.setText("");

        respawn = new Button("sauw.interface.dead.button.respawn", (int) (SCREEN_WIDTH / 16 * 5), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 5), (int) (SCREEN_WIDTH / 16 * 6), (int) (SCREEN_WIDTH / 16));
        respawn.setDefaultText();
        respawn.addEventListener(new OnClickListener() {
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
