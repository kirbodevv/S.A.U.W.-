package com.kgc.sauw.entity;

import com.badlogic.gdx.Gdx;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.BATCH;

public class ItemEntity extends Entity {
	public ItemEntity(int x, int y, int iI, int iC, int iD){
		this.type = 0;
		setExtraData("itemId", iI);
		setExtraData("itemCount", iC);
		setExtraData("itemData", iD);
		posX = x;
		posY = y;
		plW = Gdx.graphics.getWidth() / 32;
		plH = Gdx.graphics.getWidth() / 32;
		collisions = false;
	}
	@Override
	public void update() {
		super.update();
	}
	@Override
	public void render() {
		BATCH.draw(ITEMS.getTextureById((int)getExtraData("itemId")), posX, posY, plW, plH);
	}
}
