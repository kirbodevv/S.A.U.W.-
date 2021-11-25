package com.kgc.sauw.core.entity.entities.drop;

import com.badlogic.gdx.math.Vector2;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.entity.AbstractEntityRenderer;
import com.kgc.sauw.core.entity.Entity;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;

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
        setItem(SAUW.getId(id), itemsCount);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render() {
        if ((int) getExtraData("itemId") != -1)
            BATCH.draw(GameContext.getItem((int) getExtraData("itemId")).getTexture(null), getPosition().x, getPosition().y, entityBodyW, entityBodyH);
    }

    @Override
    public AbstractEntityRenderer getEntityRenderer() {
        return null;
    }
}
