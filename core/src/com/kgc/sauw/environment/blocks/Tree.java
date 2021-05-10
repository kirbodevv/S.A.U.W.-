package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Tree extends Block {
    public Tree() {
        super(ID.registeredId("block:tree", 6), TEXTURES.tree);

        BlockConfiguration.setSize(1, 2);
        BlockConfiguration.setMaxDamage(5);
        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setDrop(new int[][]{{8, 3}, {20, 1}});
        BlockConfiguration.setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
        BlockConfiguration.setInstrumentType(2);
    }
}
