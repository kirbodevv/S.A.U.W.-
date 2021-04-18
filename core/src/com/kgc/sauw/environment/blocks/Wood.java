package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.config.BlockConfiguration;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Wood extends Block {
    public Wood() {
        super(8, TEXTURES.wood);

        BlockConfiguration.setInstrumentType(2);
    }
}
