package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.core.item.Type;

public class Grass extends Item {
    public Grass() {
        super(ID.registeredId("item:grass"));
        itemConfiguration.name = Languages.getString("grass");
        itemConfiguration.type = Type.BLOCK_ITEM;
        itemConfiguration.blockId = 1;
    }
}
