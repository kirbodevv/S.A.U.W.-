package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.environment.world.World;
import com.kgc.sauw.core.utils.WorldUtils;

public class DefaultWorld extends World {
    @Override
    protected void init() {

    }

    @Override
    protected void tick() {

    }

    @Override
    public void createNewWorld() {
        WorldUtils.fillLayer(this, 2, "block:stone");
        WorldUtils.fillLayer(this, 1, "block:grass");
        WorldUtils.fillLayer(this, 0, "block:air_block");
    }
}
