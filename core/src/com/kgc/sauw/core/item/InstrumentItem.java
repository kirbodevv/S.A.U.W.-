package com.kgc.sauw.core.item;

import com.kgc.sauw.core.world.Tile;

public class InstrumentItem extends Item {
    public enum Type {
        PICKAXE, AXE, HAND, SHOVEL, NULL
    }

    @Override
    public void onClick(Tile tile) {
        if (tile.getBlock().getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}
