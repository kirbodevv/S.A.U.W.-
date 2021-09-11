package com.kgc.sauw.core.environment.block;

import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.world.Tile;

public class Air extends Block {
    public Air() {
        super(ID.registeredId("block:air_block", 4), null);
        blockConfiguration.setCollisions(false);
    }

    @Override
    public void renderTick(Tile tile) {
    }
}
