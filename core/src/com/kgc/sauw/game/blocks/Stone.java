package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.environment.item.InstrumentItem;

public class Stone extends Block {
    public Stone() {
        super(SAUW.registeredId("block:stone", 2), Resource.getTexture("Blocks/stone.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
    }
}
