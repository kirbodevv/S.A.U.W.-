package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Wood extends Block {
    public Wood() {
        super(ID.registeredId("block:wood", 8), TEXTURES.wood);

        blockConfiguration.setInstrumentType(2);
    }
}
