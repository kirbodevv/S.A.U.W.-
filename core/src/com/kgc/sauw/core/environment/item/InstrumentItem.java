package com.kgc.sauw.core.environment.item;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.core.environment.world.Tile;

public class InstrumentItem extends Item {
    public enum Type {
        PICKAXE, AXE, HAND, SHOVEL, NULL
    }

    @Override
    public void onClick(Tile tile) {
        if (GameContext.getBlock(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}
