package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class ChristmasTree extends Block {
    public ChristmasTree() {
        super(ID.registeredId("block:christmas_tree", 17), TEXTURES.christmas_tree);

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setMaxDamage(5);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{8, 3}, {20, 1}});
        blockConfiguration.setInstrumentType(2);
        blockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
    }
}
