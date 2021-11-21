package com.kgc.sauw.core.environment.block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kgc.sauw.core.environment.world.Tile;
import com.kgc.sauw.core.gui.BlockInterface;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;

public class Block {
    public int id;
    protected Sprite sprite = new Sprite();
    public BlockInterface GUI = null;
    protected BlockConfiguration blockConfiguration;

    public Block() {
        blockConfiguration = new BlockConfiguration();
    }

    public Block(Texture t0) {
        this();
        this.sprite.setRegion(t0);
    }

    public BlockConfiguration getBlockConfiguration() {
        return blockConfiguration;
    }

    public void setBlockConfiguration(BlockConfiguration blockConfiguration) {
        this.blockConfiguration = blockConfiguration;
    }

    public void setDefaultExtraData(Tile tile) {
    }

    public void onPlace(Tile tile) {
    }

    public void tick(Tile tile) {
    }

    public void tick() {
    }

    public void animationTick(Tile tile) {
    }

    public void renderTick(Tile tile) {
        float w = Blocks.getBlockById(tile.id).getBlockConfiguration().getSize().x;
        float h = Blocks.getBlockById(tile.id).getBlockConfiguration().getSize().y;
        animationTick(tile);
        sprite.setPosition(tile.x, tile.y);
        sprite.setSize(w, h);
        sprite.draw(BATCH);
    }

    public void randomTick(Tile tile) {
    }

    public void onInteractionButtonPressed(Tile tile) {
    }

    public void click(Tile tile) {
    }
}
