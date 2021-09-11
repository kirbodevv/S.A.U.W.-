package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.item.InstrumentItem;

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
