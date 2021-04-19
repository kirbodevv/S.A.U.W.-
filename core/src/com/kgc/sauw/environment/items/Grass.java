package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Grass extends Item {
    public Grass() {
        super(1);
        ItemConfiguration.name = LANGUAGES.getString("grass");
        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 1;
    }
}
