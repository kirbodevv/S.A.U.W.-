package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Sapling extends Item {
    public Sapling() {
        super(ID.registeredId("item:sapling"));

        setTexture(TEXTURES.sapling_item);

        ItemConfiguration.name = LANGUAGES.getString("sapling");
        ItemConfiguration.weight = 0.1f;
        ItemConfiguration.type = Items.Type.BLOCK_ITEM;
        ItemConfiguration.blockId = 13;
    }
}
