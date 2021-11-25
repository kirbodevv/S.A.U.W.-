package com.kgc.sauw.core.environment.block;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.register.Registry;

public class Blocks extends Registry<Block> {
    public static final Blocks INSTANCE = new Blocks();
    private static float stateTime = 0.0f;

    public static void animationTick() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public static void blockTick() {
        for (Block block : INSTANCE.objects)
            block.tick();
    }

    @Override
    public String getIDGroup() {
        return "block";
    }
}
