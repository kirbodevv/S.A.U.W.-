package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
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
        BATCH.draw(TEXTURES.inventory, X + INTERFACE_CAMERA.X, Y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16 * 8, SCREEN_WIDTH / 16);
        for (int i = 0; i < 8; i++) {
            if (Gdx.input.getX() > (i * (SCREEN_WIDTH / 16)) + X && Gdx.input.getX() < (i * (SCREEN_WIDTH / 16)) + X + (SCREEN_WIDTH / 16) && Gdx.input.getY() < SCREEN_HEIGHT && Gdx.input.getY() > SCREEN_HEIGHT - (SCREEN_WIDTH / 16)) {
                PLAYER.carriedSlot = i;
            }
        }
        BATCH.draw(TEXTURES.selected_slot, X + INTERFACE_CAMERA.X + (touchedSlot * (SCREEN_WIDTH / 16)), Y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16, SCREEN_WIDTH / 16);
        for (int i = 0; i < 8; i++) {
            if (PLAYER.hotbar[i] != -1 && PLAYER.getItemFromHotbar(i).id != 0) {
                BATCH.draw(PLAYER.getItemFromHotbar(i).t, X + INTERFACE_CAMERA.X + i * (SCREEN_WIDTH / 16) + (SCREEN_WIDTH / 64), Y + INTERFACE_CAMERA.Y + (SCREEN_WIDTH / 64), SCREEN_WIDTH / 32, SCREEN_WIDTH / 32);
                BITMAP_FONT.getData().setScale(BLOCK_SIZE / 4f / BITMAP_FONT_CAP_HEIGHT);
                BITMAP_FONT.setColor(TEXT_COLOR);
                BITMAP_FONT.draw(BATCH, "" + PLAYER.Inventory.containers.get((PLAYER.hotbar[i])).count, i * BLOCK_SIZE + X + INTERFACE_CAMERA.X - BLOCK_SIZE * 0.15f, Y + INTERFACE_CAMERA.Y + BLOCK_SIZE / 2f, BLOCK_SIZE, Align.right, false);
            }
        }
    }
}