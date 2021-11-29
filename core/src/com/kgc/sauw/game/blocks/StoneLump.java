package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.item.InstrumentItem;

public class StoneLump extends Block {
    public StoneLump() {
        super(Resource.getTexture("Blocks/stone_1.png"));

        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{SAUW.getId("item:stone"), 5}});
        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
        blockConfiguration.setCollisionsRectangleByPixels(0, 0, 32, 8, 32);
    }
}
