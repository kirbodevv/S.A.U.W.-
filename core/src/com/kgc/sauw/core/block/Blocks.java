package com.kgc.sauw.core.block;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.registry.Registry;

public class Blocks {
    public static final Registry<Block> registry = new Registry<>("block");
    private static float stateTime = 0.0f;

    public static void animationTick() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public static void blockTick() {
        for (Block block : registry.getObjects())
            block.tick();
    }
}
