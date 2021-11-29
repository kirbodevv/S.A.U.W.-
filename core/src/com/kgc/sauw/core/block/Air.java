package com.kgc.sauw.core.block;

import com.kgc.sauw.core.world.Tile;

public class Air extends Block {
    public Air() {
        blockConfiguration.setCollisions(false);
    }

    @Override
    public void renderTick(Tile tile) {
    }
}
