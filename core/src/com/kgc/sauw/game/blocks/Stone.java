package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Stone extends Block {
    public Stone() {
        super(ID.registeredId("block:stone", 2), TEXTURES.stone);

        blockConfiguration.setInstrumentType(1);
    }
}
