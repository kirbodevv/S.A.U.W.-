package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Furnace extends Item {
    public Furnace() {
        super(ID.registeredId("item:furnace"));

        setTexture(TEXTURES.furnace_item);

        ItemConfiguration.name = LANGUAGES.getString("furnace");
        ItemConfiguration.type = Items.Type.BLOCK_ITEM;
        ItemConfiguration.blockId = 11;

    }
}
