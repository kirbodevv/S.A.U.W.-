package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Dirt extends Block {
    public Dirt() {
        super(ID.registeredId("block:dirt", 12), TEXTURES.dirt);

        BlockConfiguration.setInstrumentType(4);
    }
}
