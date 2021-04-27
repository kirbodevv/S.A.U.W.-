package com.kgc.sauw.UI.Elements;

import com.badlogic.gdx.math.MathUtils;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.UI.InterfaceElement;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.math.Vector2d;
import com.kgc.sauw.environment.Items;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.resource.Textures;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;

public class Slot extends InterfaceElement {
    public int id, count, data;
    public Texture slot;

    public boolean isInventorySlot = false;
    public int inventorySlot;


    private float itemX;
    private float itemY;
    private float toItemX;
    private float toItemY;
    public SlotFunctions SF = null;
    public BitmapFont IC;


    public void setSF(SlotFunctions SF) {
        this.SF = SF;
    }

    private Interface Interface;

    public Slot(String ID, Interface Interface, float x, float y, float w, float h) {
        this.Interface = Interface;
        this.X = x;
        this.Y = y;
        setSize(w, h);
        this.ID = ID;
        itemX = x + w / 4;
        itemY = y + h / 4;
        toItemX = itemX;
        toItemY = itemY;
        IC = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        IC.scale((w / 8f) / 16f / IC.getData().capHeight);
        IC.setColor(Color.BLACK);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (slot != null) slot.dispose();
        slot = Textures.generateTexture(w / BLOCK_SIZE, h / BLOCK_SIZE, false);
    }

    @Override
    public void update(Camera2D cam) {
        super.update(cam);
        if (isTouched()) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        } else {
            toItemX = cam.X + X + width / 4;
            toItemY = cam.Y + Y + height / 4;
        }

        itemX = MathUtils.lerp(itemX, toItemX, 0.05f);
        itemY = MathUtils.lerp(itemY, toItemY, 0.05f);
    }

    @Override
    public void render(SpriteBatch batch, Camera2D cam) {
        batch.draw(slot, cam.X + X, cam.Y + Y, width, height);
    }

    @Override
    public void onClick(boolean onButton) {
        super.onClick(onButton);
        if (SF != null && onButton) {
            SF.onClick();
        }
        if (id != 0) {
            System.out.println(Interface.slots.size());
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
        itemX = X + width / 4;
        itemY = Y + height / 4;
    }

    public void itemRender() {
        if (id != 0) {
            BATCH.draw(ITEMS.getTextureById(id), itemX, itemY, width / 2, width / 2);
            IC.drawMultiLine(BATCH, count + "", itemX - width / 4, height + itemY - width / 4, width, BitmapFont.HAlignment.RIGHT);
        }
    }

    public static abstract class SlotFunctions {
        public abstract boolean isValid(int id, int count, int data, String FromSlotWithId);

        public abstract void onClick();
    }
}
