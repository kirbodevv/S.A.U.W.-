package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;

@RegistryMetadata(package_ = "sauw", id = "sand")
public class Sand extends Block {
    public Sand() {
        super(Resource.getTexture("blocks/sand.png"));
    }
}
