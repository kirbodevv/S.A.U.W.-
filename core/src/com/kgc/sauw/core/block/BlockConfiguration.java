package com.kgc.sauw.core.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.kgc.sauw.core.math.Vector2i;
import com.kgc.sauw.game.items.InstrumentItem;

public class BlockConfiguration {
    private boolean isTransparent;
    private int xSize;
    private int ySize;
    private int[][] drop;
    private InstrumentItem.Type instrumentType;
    private final Rectangle collisionsRectangle;
    private int minLightingRadius;
    private int maxLightingRadius;
    private Color lightingColor;
    private int maxDamage;
    private int blockIdAfterDestroy;
    private boolean collisions = true;

    public BlockConfiguration() {
        setTransparent(false);
        setSize(1, 1);
        setInstrumentType(InstrumentItem.Type.NULL);
        setMinLightingRadius(-1);
        setMaxDamage(1);
        setBlockIdAfterDestroy(4);
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

    public void setMinLightingRadius(int r) {
        this.minLightingRadius = r;
    }

    public void setMaxLightingRadius(int r) {
        this.maxLightingRadius = r;
    }

    public void setLightingColor(Color color) {
        lightingColor = color;
    }

    public void setMaxDamage(int md) {
        this.maxDamage = md;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setBlockIdAfterDestroy(int blockIdAfterDestroy) {
        this.blockIdAfterDestroy = blockIdAfterDestroy;
    }

    public int getBlockIdAfterDestroy() {
        return blockIdAfterDestroy;
    }

    public int getMinLightingRadius() {
        return minLightingRadius;
    }

    public int getMaxLightingRadius() {
        return maxLightingRadius;
    }

    public Color getLightingColor() {
        return lightingColor;
    }

    public void setCollisions(boolean collisions) {
        this.collisions = collisions;
    }

    public boolean getCollisions() {
        return collisions;
    }
}
