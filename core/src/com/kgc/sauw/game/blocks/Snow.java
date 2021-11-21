package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;

public class Snow extends Block {
    public Snow() {
        super(Resource.getTexture("Blocks/snow.png"));
    }
}
