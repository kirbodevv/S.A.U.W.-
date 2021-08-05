package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.InstrumentItem;

public class Grass extends Block {
    public Grass() {
        super(ID.registeredId("block:grass", 1), Resource.getTexture("Blocks/grass_1.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.SHOVEL);
    }
}
