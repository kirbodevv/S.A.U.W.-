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

public class Slot extends InterfaceElement {
    public int id, count, data;
    public Texture slot, selectedSlot;

    public boolean isInventorySlot;
    public int invSlot;

    private float itemX;
    private float itemY;
    private float toItemX;
    private float toItemY;
    private Vector2d itemDirection;
    public SlotFunctions SF = null;
    public BitmapFont IC;


    public void setSF(SlotFunctions SF) {
        this.SF = SF;
    }

    public Slot(String ID, int x, int y, int w, int h, Texture t1) {
        this.X = x;
        this.Y = y;
        this.width = w;
        this.height = h;
        float W = Gdx.graphics.getWidth();
        this.slot = Textures.generateTexture(w / (W / 16), h / (W / 16), false);
        this.ID = ID;
        this.selectedSlot = t1;
		/*this.isInventorySlot = isInventorySlot;
		this.invSlot = invSlot;*/
        itemX = x + w / 4;
        itemY = y + h / 4;
        toItemX = itemX;
        toItemY = itemY;
        itemDirection = new Vector2d();
        IC = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        IC.scale((w / 8) / 4 / IC.getData().capHeight);
        IC.setColor(Color.BLACK);
    }

    public void update(ArrayList<Slot> slots, Interface Interface, Player pl, Camera2D cam) {
        this.update(cam);
        if (isTouched()) {
            toItemX = (Gdx.input.getX() + cam.X - height / 4);
            toItemY = (Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4);
        }
        itemX = MathUtils.lerp(itemX, toItemX, 0.05f);
        itemY = MathUtils.lerp(itemY, toItemY, 0.05f);
        if (wasUp && id != 0) onClick(slots, Interface, pl, cam);
    }

    public void onClick(ArrayList<Slot> slots, Interface Interface, Player pl, Camera2D cam) {
        for (Slot slot : slots) {
            if (!slot.ID.equals(this.ID) && Maths.isLiesOnRect(slot.X, slot.Y, slot.width, slot.height, (int) itemX + width / 2, (int) itemY + height / 2)) {
                Interface.sendToSlot(this, slot, pl, cam);
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

    public void itemRender(SpriteBatch b, Items items) {
        if (id != 0) {
            b.draw(items.getTextureById(id), itemX, itemY, width / 2, width / 2);
            IC.drawMultiLine(b, count + "", itemX - width / 4, height + itemY - width / 4, width, BitmapFont.HAlignment.RIGHT);
        }
    }

    public static abstract class SlotFunctions {
        public abstract boolean isValid(int id, int count, int data, String FromSlotWithId);

        public abstract void onClick();
    }
}
