package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Dirt extends Block {
    public Dirt() {
        super(ID.registeredId("block:dirt", 12), TEXTURES.dirt);

        blockConfiguration.setInstrumentType(4);
    }
}
