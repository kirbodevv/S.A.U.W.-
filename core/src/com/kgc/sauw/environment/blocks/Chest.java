package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.gui.interfaces.Interfaces.CHEST_INTERFACE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Chest extends Block {
    public Chest() {
        super(ID.registeredId("block:chest", 5), TEXTURES.chest);

        BlockConfiguration.setInstrumentType(2);
        BlockConfiguration.setCollisionsRectangleByPixels(2, 2, 30, 9, 32);
        BlockConfiguration.setTransparent(true);

        GUI = CHEST_INTERFACE;
    }
}
