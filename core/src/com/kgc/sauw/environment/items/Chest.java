package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Chest extends Item {
    public Chest() {
        super(ID.registeredId("item:chest"));

        setTexture(TEXTURES.chest);

        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 5;
    }
}
