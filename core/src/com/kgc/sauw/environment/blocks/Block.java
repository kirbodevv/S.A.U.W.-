package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.math.Vector2i;

public class Block {
    public int id;
    public Texture t0;
    public Interface GUI = null;
    protected BlockConfiguration BlockConfiguration;

    public Block(int id, Texture t0) {
        BlockConfiguration = new BlockConfiguration();
        this.id = id;
        this.t0 = t0;
    }

    public BlockConfiguration getBlockConfiguration() {
        return BlockConfiguration;
    }


    public void onPlace(Tile tile) {
    }

    public void tick(Tile tile) {
    }

    public void randomTick(Tile tile) {
    }

    public void onInteractionButtonPressed(Tile tile) {
    }

    public void click(Tile tile) {
    }
}
