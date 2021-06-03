package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.Items;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Furnace extends Item {
    public Furnace() {
        super(ID.registeredId("item:furnace"));

        setTexture(TEXTURES.furnace_item);

        itemConfiguration.name = LANGUAGES.getString("furnace");
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 11;

    }
}
