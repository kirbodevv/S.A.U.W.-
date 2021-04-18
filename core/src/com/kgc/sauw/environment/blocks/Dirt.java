package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.config.BlockConfiguration;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Dirt extends Block {
    public Dirt() {
        super(12, TEXTURES.dirt);

        BlockConfiguration.setInstrumentType(4);
    }
}
