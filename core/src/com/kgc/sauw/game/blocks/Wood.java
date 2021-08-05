package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.InstrumentItem;

public class Wood extends Block {
    public Wood() {
        super(ID.registeredId("block:wood", 8), Resource.getTexture("Blocks/wood.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
    }
}
