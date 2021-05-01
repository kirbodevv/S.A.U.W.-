package com.kgc.sauw.config;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.kgc.sauw.math.Vector2i;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;

public class BlockConfiguration {
    private boolean isTransparent;
    private int xSize;
    private int ySize;
    private int[][] drop;
    private int InstrumentType;
    private final Rectangle collisionsRectangle;
    private int lightingRadius;
    private Color lightingColor;
    private int maxDamage;
    private int BlockIdAfterDestroy;

    public BlockConfiguration() {
        setTransparent(false);
        setSize(1, 1);
        setInstrumentType(-1);
        setLightingRadius(-1);
        setMaxDamage(1);
        setBlockIdAfterDestroy(4);
        collisionsRectangle = new Rectangle(0, 0, BLOCK_SIZE, BLOCK_SIZE);
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

    public void setInstrumentType(int instrumentType) {
        InstrumentType = instrumentType;
    }

    public int getInstrumentType() {
        return InstrumentType;
    }

    public void setCollisionsRectangleByPixels(int x, int y, int w, int h, int TextureW) {
        double scX = SCREEN_WIDTH / 16.0 / TextureW;
        collisionsRectangle.setPosition((float) scX * x, (float) scX * y);
        collisionsRectangle.setSize((float) scX * w, (float) scX * h);
    }

    public Rectangle getCollisionsRectangle() {
        return collisionsRectangle;
    }

    public void setLightingRadius(int r) {
        this.lightingRadius = r;
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
        BlockIdAfterDestroy = blockIdAfterDestroy;
    }

    public int getBlockIdAfterDestroy() {
        return BlockIdAfterDestroy;
    }

    public int getLightingRadius() {
        return lightingRadius;
    }

    public Color getLightingColor() {
        return lightingColor;
    }
}
