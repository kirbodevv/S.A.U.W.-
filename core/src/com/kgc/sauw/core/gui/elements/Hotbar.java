package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.*;

public class Hotbar extends InterfaceElement {
    private int touchedSlot = 0;

    private final Layout slotsLayout;
    private final Slot[] slots = new Slot[8];

    public Hotbar() {
        setSizeInBlocks(8f, 1f);
        slotsLayout = new Layout(Layout.Orientation.HORIZONTAL);
        slotsLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        slotsLayout.setSizeInBlocks(8, 1);
        slotsLayout.setGravity(Layout.Gravity.LEFT);
        for (int i = 0; i < 8; i++) {
            Slot slot = new Slot("HOTBAR_SLOT_" + i, null);
            slot.setSizeInBlocks(0.75f, 0.75f);
            slot.setTranslationX(i == 0 ? 0.125f : 0.25f);
            final int finalI = i;
            slot.setSF(new Slot.SlotFunctions() {
                @Override
                public boolean isValid(int id, int count, int data, String FromSlotWithId) {
                    return false;
                }

                @Override
                public void onClick() {
                    touchedSlot = finalI;
                    PLAYER.carriedSlot = finalI;
                }

                @Override
                public boolean possibleToDrag() {
                    return false;
                }
            });
            slots[i] = slot;
            slotsLayout.addElements(slot);
        }
    }

    @Override
    public void hide(boolean b) {
        super.hide(b);
        slotsLayout.hide(b);
    }

    @Override
    public void tick(Camera2D cam) {
        for (int i = 0; i < 8; i++) {
            slots[i].id = 0;
            if (PLAYER.hotbar[i] != -1 && PLAYER.getItemFromHotbar(i).id != 0) {
                slots[i].id = PLAYER.getItemFromHotbar(i).id;
                slots[i].count = PLAYER.inventory.containers.get(PLAYER.hotbar[i]).count;
            }
        }
        slotsLayout.update(cam);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        slotsLayout.setPosition(x, y);
    }

    @Override
    public void resize() {
        super.resize();
        slotsLayout.resize();
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        BATCH.draw(TEXTURES.inventory, x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y, BLOCK_SIZE * 8, BLOCK_SIZE);
        BATCH.draw(TEXTURES.selected_slot, x + INTERFACE_CAMERA.X + (touchedSlot * BLOCK_SIZE), y + INTERFACE_CAMERA.Y, BLOCK_SIZE, BLOCK_SIZE);
        BATCH.setColor(1f, 1f, 1f, 1f);
        for (int i = 0; i < 8; i++) {
            if (PLAYER.hotbar[i] != -1)
                slots[i].itemRender(PLAYER.inventory.containers.get(PLAYER.hotbar[i]));
        }
        BATCH.setColor(1f, 1f, 1f, 0.7f);
    }
}