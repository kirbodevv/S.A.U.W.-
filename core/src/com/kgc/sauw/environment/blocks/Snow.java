package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Snow extends Block {
    public Snow() {
        super(ID.registeredId("block:snow", 18), TEXTURES.snow);
    }
}
