package com.kgc.sauw.core.block;

import com.badlogic.gdx.math.Rectangle;
import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.math.Vector2i;

public class BlockConfiguration {
    public boolean isTransparent;
    public int xSize;
    public int ySize;
    public int[][] drop;
    public InstrumentItem.Type instrumentType;
    public final Rectangle collisionsRectangle;
    public int maxDamage;
    public String blockIdAfterDestroy;
    public boolean collisions = true;

    public BlockConfiguration() {
        setTransparent(false);
        setSize(1, 1);
        setInstrumentType(InstrumentItem.Type.NULL);
        setMaxDamage(1);
        setBlockIdAfterDestroy("sauw:air");
        collisionsRectangle = new Rectangle(0, 0, 1, 1);
    }


    public void setTransparent(boolean transparent) {
        isTransparent = transparent;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

    public void setSize(int x, int y) {
        this.xSize = x;
        this.ySize = y;
    }

    public Vector2i getSize() {
        return new Vector2i(xSize, ySize);
    }

    public void setDrop(int[][] drop) {
        this.drop = drop;
    }

    public int[][] getDrop() {
        return drop;
    }

    public void setInstrumentType(InstrumentItem.Type instrumentType) {
        this.instrumentType = instrumentType;
    }

    public InstrumentItem.Type getInstrumentType() {
        return instrumentType;
    }

    public void setCollisionsRectangleByPixels(float x, float y, float w, float h, int TextureW) {
        float sc = 1f / TextureW;
        collisionsRectangle.setPosition(sc * x, sc * y);
        collisionsRectangle.setSize(sc * w, sc * h);
    }

    public Rectangle getCollisionsRectangle() {
        return collisionsRectangle;
    }

    public void setMaxDamage(int md) {
        this.maxDamage = md;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setBlockIdAfterDestroy(String blockIdAfterDestroy) {
        this.blockIdAfterDestroy = blockIdAfterDestroy;
    }

    public String getBlockIdAfterDestroy() {
        return blockIdAfterDestroy;
    }

    public void setCollisions(boolean collisions) {
        this.collisions = collisions;
    }

    public boolean getCollisions() {
        return collisions;
    }
}
