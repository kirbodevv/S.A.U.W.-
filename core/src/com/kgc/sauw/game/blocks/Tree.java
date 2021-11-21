package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.item.InstrumentItem;
import com.kgc.sauw.core.utils.Resource;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Tree extends Block {
    public Tree() {
        super(Resource.getTexture("Blocks/tree.png"));

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{SAUW.getId("item:log"), 3}, {SAUW.getId("item:sapling"), 1}, {SAUW.getId("item:stick"), 6}});
        blockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
    }
}
