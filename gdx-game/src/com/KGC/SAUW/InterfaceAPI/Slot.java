package com.KGC.SAUW.InterfaceAPI;
import com.KGC.SAUW.Camera2D;
import com.KGC.SAUW.Maths;
import com.KGC.SAUW.Vector2d;
import com.KGC.SAUW.Items;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Color;
import com.KGC.SAUW.Player;
import com.KGC.SAUW.Textures;

public class Slot extends InterfaceElement {
	//public boolean isSelected = false;
	public int id, count, data;
	public Texture slot, selectedSlot;

	public boolean isInventorySlot;
	public int invSlot;

	private int itemX;
	private int itemY;

	//private boolean isItemMoving = false;
	private Vector2d itemDirection;
	public SlotFunctions SF = null;
	public BitmapFont IC;

	public void setSF(SlotFunctions SF) {
		this.SF = SF;
	}
    public Slot(String ID, int x, int y, int w, int h, Texture t1, boolean isInventorySlot, int invSlot) {
		this.X = x;
		this.Y = y;
		this.width = w;
		this.height = h;
		float W = Gdx.graphics.getWidth();
		this.slot = Textures.generateTexture(w / (W / 16), h / (W / 16), false);
		this.ID = ID;
		this.selectedSlot = t1;
		this.isInventorySlot = isInventorySlot;
		this.invSlot = invSlot;
		itemX = x + w / 4;
		itemY = y + h / 4;
		itemDirection = new Vector2d();
		IC = new BitmapFont(Gdx.files.internal("ttf.fnt"));
		IC.scale((w / 8) / 4 / IC.getData().capHeight);
		IC.setColor(0.3f, 0.3f, 0.3f, 1);
	}
    /*public void startItemMovingAnimation(slot slot) {
	 isItemMoving = true;
	 itemDirection.setfa(Maths.angleBetweenVectors(X, Y, slot.X, slot.Y));
	 itemX = slot.X + slot.width / 2;
	 itemY = slot.Y + slot.height / 2;
	 }*/
	public void update(ArrayList<Slot> slots, Interface Interface, Player pl, Camera2D cam) {
		this.update(cam);
		/*if(isTouched()){
		 int selectedSlot = -1;
		 for (int i = 0; i < slots.size(); i++) {
		 if (slots.get(i).isSelected()) selectedSlot = i;
		 }
		 if (selectedSlot == -1 && id != 0) {
		 isSelected = true;
		 }
		 }*/
		if (isTouched()) {
			itemX = Gdx.input.getX() + cam.X - width / 4;
			itemY = Gdx.graphics.getHeight() - Gdx.input.getY() + cam.Y - height / 4;
		}
	    if (wasUp) onClick(slots, Interface, pl, cam);
	}
	public void onClick(ArrayList<Slot> slots, Interface Interface, Player pl, Camera2D cam) {
		for (Slot slot : slots) {
			if (!slot.ID.equals(this.ID) && Maths.isLiesOnRect(slot.X, slot.Y, slot.width, slot.height, itemX + width / 4, itemY + height / 4)) {
				Interface.sendToSlot(this, slot, pl, cam);
			}
		}
		itemX = cam.X + X + width / 4;
		itemY = cam.Y + Y + height / 4;
	}

	@Override
	public void render(SpriteBatch batch, Camera2D cam) {
		batch.draw(slot, cam.X + X, cam.Y + Y, width, height);
	}
	public void itemRender(SpriteBatch b, Items items) {
		/*if (isItemMoving) {
		 itemX += itemDirection.x * (Gdx.graphics.getWidth() / 96);
		 itemY += itemDirection.y * (Gdx.graphics.getWidth() / 96);
		 if (Maths.distance((int)itemX, (int)itemY, X, Y) <= width) {
		 itemX = X + width / 4;
		 itemY = Y + height / 4;
		 isItemMoving = false;
		 }
		 }*/
		if (id != 0) {
			b.draw(items.getTextureById(id), itemX, itemY, width / 2, width / 2);
			IC.drawMultiLine(b, count + "", itemX - width / 4, height + itemY - width / 4, width, BitmapFont.HAlignment.RIGHT);
		}
	}
	public static abstract class SlotFunctions {
		public abstract boolean isValid(int id, int count, int data);
	}
}
