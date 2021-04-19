package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.environment.Environment.BLOCKS;

public class InstrumentItem extends Item {
    public static class Type {
        public static final int PICKAXE = 1;
        public static final int AXE = 2;
        public static final int HAND = 3;
        public static final int SHOVEL = 4;
    }

    public InstrumentItem(int id, int maxDamage, int type) {
        super(id);

        ItemConfiguration.maxData = maxDamage;
        ItemConfiguration.type = Items.Type.INSTRUMENT;
        ItemConfiguration.maxCount = 1;
        ItemConfiguration.instrumentType = type;
    }

    @Override
    public void onClick(Tile tile) {
        if(BLOCKS.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == ItemConfiguration.instrumentType) tile.hit();
    }
}
