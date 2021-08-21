package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.item.InstrumentItem;

public class Stone extends Block {
    public Stone() {
        super(ID.registeredId("block:stone", 2), Resource.getTexture("Blocks/stone.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
    }
}
