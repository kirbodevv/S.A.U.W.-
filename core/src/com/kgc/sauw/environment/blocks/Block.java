package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.ui.Interface;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.map.Tile;

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
