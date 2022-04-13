package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.World;
import com.kgc.sauw.core.utils.WorldUtils;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

public class DefaultWorld extends World {
    @Override
    public String getName() {
        return "DefaultWorld";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void tick() {

    }

    @Override
    public void createNewWorld() {
        WorldUtils.fillLayer(this, 2, "sauw:stone");
        WorldUtils.fillLayer(this, 1, "sauw:grass");
        WorldUtils.fillLayer(this, 0, "sauw:air_block");
    }

    @Override
    public AbstractWorldGenerator getWorldGenerator() {
        return null;
    }

    @Override
    public int getSkyLight() {
        return 0xFFFFFFFF;
    }
}
