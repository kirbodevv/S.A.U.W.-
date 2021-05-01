package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Grass extends Item {
    public Grass() {
        super(ID.registeredId("item:grass"));
        ItemConfiguration.name = LANGUAGES.getString("grass");
        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 1;
    }
}
