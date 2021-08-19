package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.world.Tile;

public class Air extends Block {
    public Air() {
        super(ID.registeredId("block:air_block", 4), null);
        blockConfiguration.setCollisions(false);
    }

    @Override
    public void renderTick(Tile tile) {
    }
}
