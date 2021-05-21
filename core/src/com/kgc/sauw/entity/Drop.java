package com.kgc.sauw.entity;

import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.BATCH;

public class Drop extends Entity {
    public Drop() {
        entityBodyW = 0.5f;
        entityBodyH = 0.5f;
        setSize(new Vector2(entityBodyW, entityBodyH));
        setItem(-1, 0);
    }

    @Override
    public Vector2 getBodySize() {
        return new Vector2(0.25f, 0.25f);
    }

    public void setItem(int itemId, int itemsCount) {
        setExtraData("itemId", itemId);
        setExtraData("itemCount", itemsCount);
    }

    public void setItem(String id, int itemsCount) {
        setItem(ID.get(id), itemsCount);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        if ((int) getExtraData("itemId") != -1)
            BATCH.draw(ITEMS.getItemById((int) getExtraData("itemId")).getDefaultTexture(), getPosition().x, getPosition().y, entityBodyW, entityBodyH);
    }
}
