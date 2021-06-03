package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Water extends Block {

    public Water() {
        super(ID.registeredId("block:water"), TEXTURES.water);
    }
}
