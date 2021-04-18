package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.config.BlockConfiguration;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class ChristmasTree extends Block {
    public ChristmasTree() {
        super(17, TEXTURES.christmas_tree);

        BlockConfiguration.setSize(1, 2);
        BlockConfiguration.setMaxDamage(5);
        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setDrop(new int[][]{{8, 3}, {20, 1}});
        BlockConfiguration.setInstrumentType(2);
        BlockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
    }
}
