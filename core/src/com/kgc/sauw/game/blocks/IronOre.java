package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class IronOre extends Block {

    public IronOre() {
        super(ID.registeredId("block:iron_ore", 10), TEXTURES.iron_ore);

        blockConfiguration.setMaxDamage(6);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setDrop(new int[][]{{13, 4}});
        blockConfiguration.setInstrumentType(1);
        blockConfiguration.setCollisionsRectangleByPixels(0, 0, 32, 8, 32);
    }
}
