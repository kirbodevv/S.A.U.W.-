package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.GameContext.SAUW;

public class ChristmasTree extends Block {
    public ChristmasTree() {
        super(Resource.getTexture("blocks/christmas_tree.png"));

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{SAUW.getId("item:log"), 3}, {SAUW.getId("item:sapling"), 1}});
        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
        blockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
    }
}
