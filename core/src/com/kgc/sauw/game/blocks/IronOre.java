package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.InstrumentItem;

public class IronOre extends Block {

    public IronOre() {
        super(ID.registeredId("block:iron_ore", 10), Resource.getTexture("Blocks/iron_ore.png"));

        blockConfiguration.setMaxDamage(6);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{ID.get("item:iron_ore"), 4}});
        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
        blockConfiguration.setCollisionsRectangleByPixels(0, 0, 32, 8, 32);
    }
}
