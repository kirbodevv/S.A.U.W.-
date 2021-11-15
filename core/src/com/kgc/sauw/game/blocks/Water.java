package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;

public class Water extends Block {

    public Water() {
        super(SAUW.registeredId("block:water"), Resource.getTexture("Blocks/water.png"));
    }
}
