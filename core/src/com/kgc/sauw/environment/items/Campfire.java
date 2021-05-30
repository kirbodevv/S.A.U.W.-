package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Campfire extends Item {
    public Campfire() {
        super(ID.registeredId("item:campfire"));

        setTexture(TEXTURES.campfire);

        itemConfiguration.name = LANGUAGES.getString("campfire");
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 15;

    }
}
