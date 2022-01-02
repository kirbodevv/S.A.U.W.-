package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.item.InstrumentItem;

public class Dirt extends Block {
    public Dirt() {
        super(Resource.getTexture("blocks/dirt.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.SHOVEL);
    }
}
