package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Snow extends Block {
    public Snow() {
        super(ID.registeredId("block:snow", 18), TEXTURES.snow);
    }
}
