package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class IronOre extends Block {

    public IronOre() {
        super(ID.registeredId("block:iron_ore", 10), TEXTURES.iron_ore);

        BlockConfiguration.setMaxDamage(6);
        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setDrop(new int[][]{{13, 4}});
        BlockConfiguration.setInstrumentType(1);
        BlockConfiguration.setCollisionsRectangleByPixels(0, 0, 32, 8, 32);
    }
}
