package com.kgc.sauw.environment.blocks;

import static com.kgc.sauw.ui.interfaces.Interfaces.CHEST_INTERFACE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Chest extends Block {
    public Chest() {
        super(5, TEXTURES.chest);

        BlockConfiguration.setInstrumentType(2);
        BlockConfiguration.setCollisionsRectangleByPixels(2, 2, 30, 9, 32);
        BlockConfiguration.setTransparent(true);

        GUI = CHEST_INTERFACE;
    }
}
