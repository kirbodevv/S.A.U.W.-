package com.kgc.sauw.environment.blocks;

import static com.kgc.sauw.UI.Interfaces.Interfaces.CHEST_INTERFACE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Chest extends Block {
    public Chest() {
        super(5, TEXTURES.chest);

        BlockConfiguration.setInstrumentType(2);

        GUI = CHEST_INTERFACE;
    }
}
