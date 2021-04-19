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
import static com.kgc.sauw.graphic.Graphic.BATCH;

public class Slot extends InterfaceElement {
    public int id, count, data;
    public Texture slot;

    public boolean isInventorySlot;
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

    public Slot(String ID, int x, int y, int w, int h) {
        this.X = x;
        this.Y = y;
        this.width = w;
        this.height = h;
        float W = Gdx.graphics.getWidth();
        this.slot = Textures.generateTexture(w / (W / 16), h / (W / 16), false);
        this.ID = ID;
        itemX = x + w / 4;
        itemY = y + h / 4;
        toItemX = itemX;
        toItemY = itemY;
        IC = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        IC.scale((w / 8f) / 16f / IC.getData().capHeight);
        IC.setColor(Color.BLACK);
    }

    public void update(ArrayList<Slot> slots, Interface Interface, Player pl, Camera2D cam) {
        this.update(cam);
        if (isTouched()) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        }
        itemX = MathUtils.lerp(itemX, toItemX, 0.1f);
        itemY = MathUtils.lerp(itemY, toItemY, 0.1f);
        if (wasUp && id != 0) onClick(slots, Interface, cam);
    }

    public void onClick(ArrayList<Slot> slots, Interface Interface, Camera2D cam) {
        for (Slot slot : slots) {
            if (!slot.ID.equals(this.ID) && Maths.isLiesOnRect(slot.X, slot.Y, slot.width, slot.height, (int) itemX + width / 2, (int) itemY + height / 2)) {
                Interface.sendToSlot(this, slot);
            }
        }
        toItemX = cam.X + X + width / 4;
        toItemY = cam.Y + Y + height / 4;
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
