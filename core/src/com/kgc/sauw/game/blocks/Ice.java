package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.block.SlipperyBlock;
import com.kgc.sauw.core.registry.Object;
import com.kgc.sauw.core.resource.Resource;

@Object(package_ = "sauw", id = "ice")
public class Ice extends Block implements SlipperyBlock {
    public Ice() {
        super(Resource.getTexture("blocks/ice.png"));
    }

    @Override
    public float getFriction() {
        return 5f;
    }
}