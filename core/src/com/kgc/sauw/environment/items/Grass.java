package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Grass extends Item {
    public Grass() {
        super(ID.registeredId("item:grass"));
        itemConfiguration.name = LANGUAGES.getString("grass");
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 1;
    }
}
