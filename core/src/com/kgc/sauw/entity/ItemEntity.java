package com.kgc.sauw.entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.environment.Items;
import com.badlogic.gdx.Gdx;

public class ItemEntity extends Entity {
	public ItemEntity(int x, int y, int iI, int iC, int iD, Items items){
		super(items);
		this.type = 0;
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
