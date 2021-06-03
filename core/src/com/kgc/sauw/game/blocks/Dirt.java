package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Dirt extends Block {
    public Dirt() {
        super(ID.registeredId("block:dirt", 12), TEXTURES.dirt);

        blockConfiguration.setInstrumentType(4);
    }
}
