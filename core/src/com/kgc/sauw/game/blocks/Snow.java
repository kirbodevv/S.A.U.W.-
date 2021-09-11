package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.ID;

public class Snow extends Block {
    public Snow() {
        super(ID.registeredId("block:snow", 18), Resource.getTexture("Blocks/snow.png"));
    }
}
