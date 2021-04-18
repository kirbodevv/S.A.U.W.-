package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.config.BlockConfiguration;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Stone extends Block {
    public Stone() {
        super(2, TEXTURES.stone);

        BlockConfiguration.setInstrumentType(1);
    }
}
