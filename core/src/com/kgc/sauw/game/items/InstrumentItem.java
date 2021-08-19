package com.kgc.sauw.game.items;

import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.world.Tile;

public class InstrumentItem extends Item {
    public enum Type {
        PICKAXE, AXE, HAND, SHOVEL, NULL
    }

    public InstrumentItem(int id, int maxDamage, Type type) {
        super(id);

        itemConfiguration.maxDamage = maxDamage;
        itemConfiguration.type = com.kgc.sauw.core.item.Type.INSTRUMENT;
        itemConfiguration.maxCount = 1;
        itemConfiguration.instrumentType = type;
    }

    @Override
    public void onClick(Tile tile) {
        System.out.println(tile.id);
        if (Blocks.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}
