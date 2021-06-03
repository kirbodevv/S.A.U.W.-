package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.Items;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Sapling extends Item {
    public Sapling() {
        super(ID.registeredId("item:sapling"));

        setTexture(TEXTURES.sapling_item);

        itemConfiguration.name = LANGUAGES.getString("sapling");
        itemConfiguration.weight = 0.1f;
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 13;
    }
}
