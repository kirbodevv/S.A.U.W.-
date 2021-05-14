package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class StoneLump extends Block {
    public StoneLump() {
        super(ID.registeredId("block:stone_lump", 9), TEXTURES.stone_1);

        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{12, 5}});
        blockConfiguration.setInstrumentType(1);
        blockConfiguration.setCollisionsRectangleByPixels(0, 0, 32, 8, 32);
    }
}
