package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Grass extends Block {
    public Grass() {
        super(ID.registeredId("block:grass", 1), TEXTURES.grass0);

        BlockConfiguration.setInstrumentType(4);
    }
}
