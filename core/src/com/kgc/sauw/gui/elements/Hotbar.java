package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.utils.Camera2D;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;

public class Hotbar extends InterfaceElement {
    private int touchedSlot = 0;

    public Hotbar() {
        setSizeInBlocks(8f, 1f);
    }

    @Override
    public void tick(Camera2D cam) {
        if (isTouched()) {
            float localX = cam.X + Gdx.input.getX() - X;
            if (localX > X + width) localX = X + width;
            touchedSlot = (int) (localX / BLOCK_SIZE);
            if (touchedSlot < 0) touchedSlot = 0;
            if (touchedSlot > 7) touchedSlot = 7;
        }
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        BATCH.setColor(1, 1, 1, 0.8f);
        BATCH.draw(TEXTURES.inventory, X + INTERFACE_CAMERA.X, Y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16 * 8, SCREEN_WIDTH / 16);
        for (int i = 0; i < 8; i++) {
            if (Gdx.input.getX() > (i * (SCREEN_WIDTH / 16)) + X && Gdx.input.getX() < (i * (SCREEN_WIDTH / 16)) + X + (SCREEN_WIDTH / 16) && Gdx.input.getY() < SCREEN_HEIGHT && Gdx.input.getY() > SCREEN_HEIGHT - (SCREEN_WIDTH / 16)) {
                PLAYER.carriedSlot = i;
            }
        }
        BATCH.setColor(1, 1, 1, 1);
        BATCH.draw(TEXTURES.selected_slot, X + INTERFACE_CAMERA.X + (touchedSlot * (SCREEN_WIDTH / 16)), Y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16, SCREEN_WIDTH / 16);
        for (int i = 0; i < 8; i++) {
            if (PLAYER.hotbar[i] != -1 && PLAYER.getItemFromHotbar(i).id != 0) {
                BATCH.draw(PLAYER.getItemFromHotbar(i).t, X + INTERFACE_CAMERA.X + i * (SCREEN_WIDTH / 16) + (SCREEN_WIDTH / 64), Y + INTERFACE_CAMERA.Y + (SCREEN_WIDTH / 64), SCREEN_WIDTH / 32, SCREEN_WIDTH / 32);
                //itemsCount.draw(BATCH, "" + pl.Inventory.containers.get((pl.hotbar[i])).count, i * (SCREEN_WIDTH / 16) + x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y + (SCREEN_WIDTH / 16));
            }
        }
    }
}