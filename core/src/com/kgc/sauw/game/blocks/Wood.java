package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Wood extends Block {
    public Wood() {
        super(ID.registeredId("block:wood", 8), TEXTURES.wood);

        blockConfiguration.setInstrumentType(2);
    }
}
