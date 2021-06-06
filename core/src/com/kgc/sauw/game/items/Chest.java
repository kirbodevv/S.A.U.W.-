package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.environment.Items;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Chest extends Item {
    public Chest() {
        super(ID.registeredId("item:chest"));

        setTexture(TEXTURES.chest);

        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 5;
    }
}
