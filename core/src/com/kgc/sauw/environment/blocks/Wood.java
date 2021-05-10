package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Wood extends Block {
    public Wood() {
        super(ID.registeredId("block:wood", 8), TEXTURES.wood);

        BlockConfiguration.setInstrumentType(2);
    }
}
