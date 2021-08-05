package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;

public class Water extends Block {

    public Water() {
        super(ID.registeredId("block:water"), Resource.getTexture("Blocks/water.png"));
    }
}
