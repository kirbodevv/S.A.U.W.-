package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.item.InstrumentItem;
import com.kgc.sauw.core.utils.Resource;

public class Wood extends Block {
    public Wood() {
        super(Resource.getTexture("Blocks/wood.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
    }
}
