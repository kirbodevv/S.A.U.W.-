package com.kgc.sauw.core.environment.item;

import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.core.environment.world.Tile;

public class InstrumentItem extends Item {
    public enum Type {
        PICKAXE, AXE, HAND, SHOVEL, NULL
    }

    public InstrumentItem(String id) {
        super(id);
    }

    @Override
    public void onClick(Tile tile) {
        if (Blocks.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}
