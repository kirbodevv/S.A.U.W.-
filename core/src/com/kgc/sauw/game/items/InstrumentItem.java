package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.game.environment.Items;
import com.kgc.sauw.core.map.Tile;

import static com.kgc.sauw.game.environment.Environment.BLOCKS;

public class InstrumentItem extends Item {
    public static class Type {
        public static final int PICKAXE = 1;
        public static final int AXE = 2;
        public static final int HAND = 3;
        public static final int SHOVEL = 4;
    }

    public InstrumentItem(int id, int maxDamage, int type) {
        super(id);

        itemConfiguration.maxDamage = maxDamage;
        itemConfiguration.type = Items.Type.INSTRUMENT;
        itemConfiguration.maxCount = 1;
        itemConfiguration.instrumentType = type;
    }

    @Override
    public void onClick(Tile tile) {
        System.out.println(tile.id);
        if(BLOCKS.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType) tile.hit();
    }
}
