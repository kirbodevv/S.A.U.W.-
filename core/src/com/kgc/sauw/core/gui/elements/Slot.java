package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.resource.TextureGenerator;
import com.kgc.sauw.core.utils.Camera2D;

import static com.kgc.sauw.core.environment.Environment.ITEMS;
import static com.kgc.sauw.core.graphic.Graphic.*;

public class Slot extends InterfaceElement {
    public int id, count, data;
    public Texture slot;
    private Texture icon;

    public boolean isInventorySlot = false;
    public int inventorySlot;


    private float itemX;
    private float itemY;
    public SlotFunctions SF = null;

    public void setSF(SlotFunctions SF) {
        this.SF = SF;
    }

    private final Interface Interface;
    public static final ProgressBar itemDamageProgressBar;

    static {
        itemDamageProgressBar = new ProgressBar(true, false);
        itemDamageProgressBar.setSizeInBlocks(2, 0.5f);
    }

    public Slot(String ID, Interface Interface) {
        this.Interface = Interface;
        this.ID = ID;
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (slot != null) slot.dispose();
        slot = TextureGenerator.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);
    }

    @Override
    public void tick(Camera2D cam) {
        float toItemX;
        float toItemY;
        if (isTouched() && (SF == null || SF.possibleToDrag())) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        } else {
            toItemX = cam.X + x;
            toItemY = cam.Y + y;
        }

        itemX = MathUtils.lerp(itemX, toItemX, 0.05f);
        itemY = MathUtils.lerp(itemY, toItemY, 0.05f);
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        batch.draw(slot, cam.X + x, cam.Y + y, width, height);
        if (icon != null && id == 0) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(icon, cam.X + x, cam.Y + y, width, height);
            batch.setColor(1, 1, 1, 1);
        }
        if (id != 0 && ITEMS.getItemById(id).getItemConfiguration().maxDamage != 0 &&
                Maths.isLiesOnRect(x, y, width, height, Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY()) &&
                !Gdx.input.isTouched()) {
            itemDamageProgressBar.hide(false);
            itemDamageProgressBar.setPosition(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY() - itemDamageProgressBar.height);
            itemDamageProgressBar.setColor(0, 255, 0);
            itemDamageProgressBar.setMaxValue(ITEMS.getItemById(id).getItemConfiguration().maxDamage);
            itemDamageProgressBar.setValue(ITEMS.getItemById(id).getItemConfiguration().maxDamage - data);
        }
    }

    @Override
    public void onClick(boolean onButton) {
        super.onClick(onButton);
        if (SF != null && onButton) {
            SF.onClick();
        }
        if (id != 0 && Interface != null) {
            for (Slot slot : Interface.slots) {
                if (!slot.ID.equals(this.ID) && Maths.isLiesOnRect(slot.x, slot.y, slot.width, slot.height, INTERFACE_CAMERA.touchX(), INTERFACE_CAMERA.touchY())) {
                    Interface.sendToSlot(this, slot);
                }
            }
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        itemX = this.x;
        itemY = this.y;
    }

    public void itemRender(Container container) {
        if (id != 0) {
            if (container == null) BATCH.draw(ITEMS.getItemById(id).getDefaultTexture(), itemX, itemY, width, height);
            else BATCH.draw(ITEMS.getItemById(id).getTexture(container), itemX, itemY, width, height);

            BITMAP_FONT.getData().setScale((width / 3f) / BITMAP_FONT_CAP_HEIGHT);
            GLYPH_LAYOUT.setText(BITMAP_FONT, count + "");
            BITMAP_FONT.draw(BATCH, count + "", itemX, itemY + GLYPH_LAYOUT.height + width / 32f, width, Align.right, false);
        }
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void setIconFromItem(String id) {
        setIcon(ITEMS.getItemById(com.kgc.sauw.core.utils.ID.get(id)).getDefaultTexture());
    }

    public static abstract class SlotFunctions {
        public abstract boolean isValid(int id, int count, int data, String FromSlotWithId);

        public abstract void onClick();

        public abstract boolean possibleToDrag();
    }
}