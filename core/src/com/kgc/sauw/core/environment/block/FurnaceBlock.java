package com.kgc.sauw.core.environment.block;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kgc.sauw.core.environment.world.Tile;

public class FurnaceBlock extends Block implements HeatingBlock {
    Sprite sprite = new Sprite();

    public FurnaceBlock(Texture t0, Texture t1) {
    }

    @Override
    public float heatTemperature(Tile tile) {
        return 0;
    }
}
