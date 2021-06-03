package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.gui.Interfaces.CHEST_INTERFACE;

public class Chest extends Block {
    public Chest() {
        super(ID.registeredId("block:chest", 5), TEXTURES.chest);

        blockConfiguration.setInstrumentType(2);
        blockConfiguration.setCollisionsRectangleByPixels(2, 2, 30, 9, 32);
        blockConfiguration.setTransparent(true);

        GUI = CHEST_INTERFACE;
    }
}
