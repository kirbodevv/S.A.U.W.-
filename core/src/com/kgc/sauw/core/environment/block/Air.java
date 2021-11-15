package com.kgc.sauw.core.environment.block;

import com.kgc.sauw.core.environment.world.Tile;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Air extends Block {
    public Air() {
        super(SAUW.registeredId("block:air_block", 4), null);
        blockConfiguration.setCollisions(false);
    }

    @Override
    public void renderTick(Tile tile) {
    }
}
