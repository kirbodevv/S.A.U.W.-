package com.kgc.sauw.UI.Interfaces;

import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Interface;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.LANGUAGES;
import static com.kgc.sauw.graphic.Graphic.SCREEN_HEIGHT;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;

public class DeadInterface extends Interface {
    Button respawn;

    public DeadInterface() {
        super(InterfaceSizes.STANDART, "DEAD_INTERFACE");
        setHeaderText("").isBlockInterface(false);

        respawn = new Button("DEAD_INTERFACE_RESPAWN_BUTTON", (int) (SCREEN_WIDTH / 16 * 5), (int) (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 5), (int) (SCREEN_WIDTH / 16 * 6), (int) (SCREEN_WIDTH / 16));
        //respawn.setTextColor(Color.BLACK);
        respawn.setText(LANGUAGES.getString("respawn"));
        respawn.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                PLAYER.spawn();
                close();
            }
        });
        exitButton.hide(true);
        buttons.add(respawn);
    }
}
