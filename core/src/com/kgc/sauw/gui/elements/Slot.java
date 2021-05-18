package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.InventoryContainer;
import com.kgc.sauw.gui.Interface;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;

public class Slot extends InterfaceElement {
    public int id, count, data;
    public Texture slot;
    private Texture icon;

    public boolean isInventorySlot = false;
    public int inventorySlot;


    private float itemX;
    private float itemY;
    public SlotFunctions SF = null;
    public BitmapFont bitmapFont;
    public GlyphLayout glyphLayout;

    public void setSF(SlotFunctions SF) {
        this.SF = SF;
    }

    private final Interface Interface;

    public Slot(String ID, Interface Interface) {
        this.Interface = Interface;
        this.ID = ID;
        bitmapFont = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        glyphLayout = new GlyphLayout();
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (slot != null) slot.dispose();
        slot = Textures.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);
        bitmapFont.getData().setScale((w / 2f) / bitmapFont.getCapHeight());
        bitmapFont.setColor(Color.BLACK);
    }

    @Override
    public void tick(Camera2D cam) {
        float toItemX;
        float toItemY;
        if (isTouched() && (SF == null || SF.possibleToDrag())) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        } else {
            toItemX = cam.X + X;
            toItemY = cam.Y + Y;
        }

        itemX = MathUtils.lerp(itemX, toItemX, 0.05f);
        itemY = MathUtils.lerp(itemY, toItemY, 0.05f);
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        batch.draw(slot, cam.X + X, cam.Y + Y, width, height);
        if (icon != null && id == 0) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(icon, cam.X + X, cam.Y + Y, width, height);
            batch.setColor(1, 1, 1, 1);
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
                if (!slot.ID.equals(this.ID) && Maths.isLiesOnRect(slot.X, slot.Y, slot.width, slot.height, INTERFACE_CAMERA.touchX(), INTERFACE_CAMERA.touchY())) {
                    Interface.sendToSlot(this, slot);
                }
            }
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        itemX = X;
        itemY = Y;
    }

    public void itemRender(InventoryContainer container) {
        if (id != 0) {
            if (container == null) BATCH.draw(ITEMS.getItemById(id).getDefaultTexture(), itemX, itemY, width, height);
            else BATCH.draw(ITEMS.getItemById(id).getTexture(container), itemX, itemY, width, height);

            glyphLayout.setText(bitmapFont, count + "");
            bitmapFont.draw(BATCH, count + "", itemX, itemY + glyphLayout.height + width / 32f, width, Align.right, false);
        }
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public void setIconFromItem(String id) {
        setIcon(ITEMS.getItemById(com.kgc.sauw.utils.ID.get(id)).getDefaultTexture());
    }

    public static abstract class SlotFunctions {
        public abstract boolean isValid(int id, int count, int data, String FromSlotWithId);

        public abstract void onClick();

        public abstract boolean possibleToDrag();
    }
}
