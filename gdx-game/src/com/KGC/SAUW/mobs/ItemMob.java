package com.KGC.SAUW.mobs;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.KGC.SAUW.Items;
import com.badlogic.gdx.Gdx;

public class ItemMob extends Mob {
	/*public int itemID;
	public int itemCount;
	public int itemData;*/
	public Items items;
	public ItemMob(int x, int y, int iI, int iC, int iD, Items items){
		setExtraData("itemId", iI);
		setExtraData("itemCount", iC);
		setExtraData("itemData", iD);
		posX = x;
		posY = y;
		this.items = items;
		plW = Gdx.graphics.getWidth() / 32;
		plH = Gdx.graphics.getWidth() / 32;
		collisions = false;
	}
	public void ItemMob(Items items){
		type = "Item";
		this.items = items;
		plW = Gdx.graphics.getWidth() / 32;
		plH = Gdx.graphics.getWidth() / 32;
	}
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(SpriteBatch b) {
		super.render(b);
		b.draw(items.getTextureById((int)getExtraData("itemId")), posX, posY, plW, plH);
	}
}
