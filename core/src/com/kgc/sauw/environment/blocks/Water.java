package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Water extends Block {

    public Water() {
        super(ID.registeredId("block:water"), TEXTURES.water);
    }
}
