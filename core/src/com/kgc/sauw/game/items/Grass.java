package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.Items;

import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Grass extends Item {
    public Grass() {
        super(ID.registeredId("item:grass"));
        itemConfiguration.name = LANGUAGES.getString("grass");
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 1;
    }
}
