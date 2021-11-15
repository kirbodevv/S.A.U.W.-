package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.environment.item.InstrumentItem;

public class ChristmasTree extends Block {
    public ChristmasTree() {
        super(SAUW.registeredId("block:christmas_tree", 17), Resource.getTexture("Blocks/christmas_tree.png"));

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{SAUW.getId("item:log"), 3}, {SAUW.getId("item:sapling"), 1}});
        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
        blockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
    }
}
