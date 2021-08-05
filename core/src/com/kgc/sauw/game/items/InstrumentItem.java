package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.game.environment.Items;

import static com.kgc.sauw.game.environment.Environment.BLOCKS;

public class InstrumentItem extends Item {
    public enum Type {
        PICKAXE, AXE, HAND, SHOVEL, NULL
    }

    public InstrumentItem(int id, int maxDamage, Type type) {
        super(id);

        itemConfiguration.maxDamage = maxDamage;
        itemConfiguration.type = Items.Type.INSTRUMENT;
        itemConfiguration.maxCount = 1;
        itemConfiguration.instrumentType = type;
    }

    @Override
    public void onClick(Tile tile) {
        System.out.println(tile.id);
        if (BLOCKS.getBlockById(tile.id).getBlockConfiguration().getInstrumentType() == itemConfiguration.instrumentType)
            tile.hit();
    }
}
