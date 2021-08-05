package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.InstrumentItem;

public class Dirt extends Block {
    public Dirt() {
        super(ID.registeredId("block:dirt", 12), Resource.getTexture("Blocks/dirt.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.SHOVEL);
    }
}
