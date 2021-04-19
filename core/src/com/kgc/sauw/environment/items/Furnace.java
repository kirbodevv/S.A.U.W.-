package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Furnace extends Item {
    public Furnace() {
        super(16);

        this.t = TEXTURES.furnace_item;

        ItemConfiguration.name = LANGUAGES.getString("furnace");
        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 11;

    }
}
