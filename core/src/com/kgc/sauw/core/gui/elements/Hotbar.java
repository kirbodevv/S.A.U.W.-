package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.utils.Camera2D;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.*;

public class Hotbar extends InterfaceElement {
    private int touchedSlot = 0;

    private final Layout slotsLayout;
    private final Slot[] slots = new Slot[8];

    private final Texture selected_slot_texture = Resource.getTexture("interface/selected_slot.png");

    public Hotbar() {
        setSizeInBlocks(6f, 0.75f);
        slotsLayout = new Layout(Layout.Orientation.HORIZONTAL);
        slotsLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        slotsLayout.setSizeInBlocks(6f, 0.75f);
        slotsLayout.setGravity(Layout.Gravity.LEFT);
        ElementSkin hotbar_slot_skin = new ElementSkin(Skins.round_down);
        hotbar_slot_skin.setColor(0x777777FF);
        for (int i = 0; i < 8; i++) {
            Slot slot = new Slot("HOTBAR_SLOT_" + i, null);
            slot.setSizeInBlocks(0.5625f, 0.5625f);
            slot.setTranslationX(i == 0 ? 0.09375f : 0.1875f);
            slot.setTextColor(new Color(0xFFCB3CFF));
            slot.slot = hotbar_slot_skin;
            final int finalI = i;
            slot.setSF(new Slot.SlotFunctions() {
                @Override
                public boolean isValid(Container container, String fromSlotWithId) {
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

                @Override
                public void onItemSwapping(Container fromContainer) {

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
            if (PLAYER.hotbar[i] != null) {
                slots[i].setContainer(PLAYER.hotbar[i]);
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
        slotsLayout.render(batch, cam);
        BATCH.draw(selected_slot_texture, x + INTERFACE_CAMERA.X + (touchedSlot * BLOCK_SIZE * 0.75f), y + INTERFACE_CAMERA.Y, 0.75f * BLOCK_SIZE, 0.75f * BLOCK_SIZE);
        BATCH.setColor(1f, 1f, 1f, 1f);
        for (int i = 0; i < 8; i++) {
            if (PLAYER.hotbar[i] != null)
                slots[i].itemRender();
        }
        BATCH.setColor(1f, 1f, 1f, 0.7f);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }
}