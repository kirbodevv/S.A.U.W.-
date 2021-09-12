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

    public InstrumentItem(int id, int maxDamage, Type type) {
        super(id);

        itemConfiguration.maxDamage = maxDamage;
        itemConfiguration.type = com.kgc.sauw.core.environment.item.Type.INSTRUMENT;
        itemConfiguration.maxCount = 1;
        itemConfiguration.instrumentType = type;
    }

    @Override
    public void onClick(Tile tile) {
        if (Blocks.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}