package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Grass extends Block {
    public Grass() {
        super(ID.registeredId("block:grass", 1), TEXTURES.grass0);

        blockConfiguration.setInstrumentType(4);
    }
}
