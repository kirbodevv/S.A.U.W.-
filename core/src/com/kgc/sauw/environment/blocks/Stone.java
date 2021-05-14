package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Stone extends Block {
    public Stone() {
        super(ID.registeredId("block:stone", 2), TEXTURES.stone);

        blockConfiguration.setInstrumentType(1);
    }
}
