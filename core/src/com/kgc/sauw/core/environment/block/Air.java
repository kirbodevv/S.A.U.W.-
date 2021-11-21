package com.kgc.sauw.core.environment.block;

import com.kgc.sauw.core.environment.world.Tile;

public class Air extends Block {
    public Air() {
        blockConfiguration.setCollisions(false);
    }

    @Override
    public void renderTick(Tile tile) {
    }
}
