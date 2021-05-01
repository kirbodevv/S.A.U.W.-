package com.kgc.sauw.ui.elements;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.entity.Player;

import static com.kgc.sauw.ui.interfaces.Interfaces.INVENTORY_INTERFACE;
import static com.kgc.sauw.graphic.Graphic.*;

public class InventoryElement {
    private BitmapFont itemsCount = new BitmapFont(Gdx.files.internal("ttf.fnt"));;
    private Texture Texture;
    private Texture Texture2;
    private boolean hidden = false;
    private boolean isTouched = false;
    private int x, y;
    private Button openButton;

    public InventoryElement(Texture Texture, Texture Texture2, int x, int y) {
        itemsCount.setColor(Color.BLACK);
        itemsCount.scale(SCREEN_WIDTH / 2500);
        this.Texture = Texture;
        this.Texture2 = Texture2;
        this.x = x;
        this.y = y;
        openButton = new Button("INVENTORY_OPEN_BUTTON", x + BLOCK_SIZE * 8, y, BLOCK_SIZE, BLOCK_SIZE, TEXTURES.extraButton_0, TEXTURES.extraButton_1);

        openButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                INVENTORY_INTERFACE.open();
            }
        });
    }

    public void update() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            openButton.hide(true);
        }
        if (!hidden) {
            openButton.update(INTERFACE_CAMERA);
            if (Gdx.input.isTouched()) {
                isTouched = Gdx.input.getX() >= x && Gdx.input.getX() <= 8 * (SCREEN_WIDTH / 16) + x && Gdx.input.getY() < SCREEN_HEIGHT && Gdx.input.getY() > SCREEN_HEIGHT - (SCREEN_WIDTH / 16);
            }
        }
    }

    public void render(Player pl) {
        if (!hidden) {
            BATCH.setColor(1, 1, 1, 0.8f);
            BATCH.draw(Texture, x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16 * 8, SCREEN_WIDTH / 16);
            for (int i = 0; i < 8; i++) {
                if (Gdx.input.getX() > (i * (SCREEN_WIDTH / 16)) + x && Gdx.input.getX() < (i * (SCREEN_WIDTH / 16)) + x + (SCREEN_WIDTH / 16) && Gdx.input.getY() < SCREEN_HEIGHT && Gdx.input.getY() > SCREEN_HEIGHT - (SCREEN_WIDTH / 16)) {
                    pl.carriedSlot = i;
                }
            }
            openButton.render(BATCH, INTERFACE_CAMERA);
            BATCH.setColor(1, 1, 1, 1);
            BATCH.draw(Texture2, x + INTERFACE_CAMERA.X + (pl.carriedSlot * (SCREEN_WIDTH / 16)), y + INTERFACE_CAMERA.Y, SCREEN_WIDTH / 16, SCREEN_WIDTH / 16);
            for (int i = 0; i < 8; i++) {
                if (pl.hotbar[i] != -1 && pl.getItemFromHotbar(i).id != 0) {
                    BATCH.draw(pl.getItemFromHotbar(i).t, x + INTERFACE_CAMERA.X + i * (SCREEN_WIDTH / 16) + (SCREEN_WIDTH / 64), y + INTERFACE_CAMERA.Y + (SCREEN_WIDTH / 64), SCREEN_WIDTH / 32, SCREEN_WIDTH / 32);
                    itemsCount.draw(BATCH, "" + pl.Inventory.containers.get((pl.hotbar[i])).count, i * (SCREEN_WIDTH / 16) + x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y + (SCREEN_WIDTH / 16));
                }
            }
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void hide(boolean hide) {
        this.hidden = hide;
    }
}
