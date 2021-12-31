package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.game.gui.Interfaces.CHEST_INTERFACE;

public class Chest extends Block {
    public Chest() {
        super(Resource.getTexture("Blocks/chest.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
        blockConfiguration.setCollisionsRectangleByPixels(2, 2, 30, 9, 32);
        blockConfiguration.setTransparent(true);

        GUI = CHEST_INTERFACE;
    }
}
