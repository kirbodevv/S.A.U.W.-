package com.kgc.sauw.entity;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.BATCH;

public class ItemEntityL extends EntityL {
    public ItemEntityL(float x, float y, int iI, int iC, int iD) {
        this.type = 0;
        setExtraData("itemId", iI);
        setExtraData("itemCount", iC);
        setExtraData("itemData", iD);
        posX = (int) x;
        posY = (int) y;
        plW = 0.5f;
        plH = 0.5f;
        collisions = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        BATCH.draw(ITEMS.getItemById((int) getExtraData("itemId")).getDefaultTexture(), posX, posY, plW, plH);
    }
}
