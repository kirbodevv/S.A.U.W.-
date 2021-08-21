package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.item.InstrumentItem;

public class ChristmasTree extends Block {
    public ChristmasTree() {
        super(ID.registeredId("block:christmas_tree", 17), Resource.getTexture("Blocks/christmas_tree.png"));

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{ID.get("item:log"), 3}, {ID.get("item:sapling"), 1}});
        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
        blockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
    }
}
