package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Sapling extends Item {
    public Sapling() {
        super(20);

        this.t = TEXTURES.sapling_item;

        ItemConfiguration.name = LANGUAGES.getString("sapling");
        ItemConfiguration.weight = 0.1f;
        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 13;
    }
}
