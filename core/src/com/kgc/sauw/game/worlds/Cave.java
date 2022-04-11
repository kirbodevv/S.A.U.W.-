package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.World;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

public class Cave extends World {
    @Override
    public String getName() {
        return "cave";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void tick() {

    }

    @Override
    public AbstractWorldGenerator getWorldGenerator() {
        return CaveGenerator.INSTANCE;
    }

    @Override
    public int getSkyLight() {
        return 0;
    }
}
