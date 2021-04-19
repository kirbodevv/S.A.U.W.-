package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Campfire extends Item {
    public Campfire() {
        super(23);

        this.t = TEXTURES.campfire;

        ItemConfiguration.name = LANGUAGES.getString("campfire");
        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 15;

    }
}
