package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.item.InstrumentItem;

public class Wood extends Block {
    public Wood() {
        super(ID.registeredId("block:wood", 8), Resource.getTexture("Blocks/wood.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
    }
}
