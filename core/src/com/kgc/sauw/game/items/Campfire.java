package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.environment.Items;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Campfire extends Item {
    public Campfire() {
        super(ID.registeredId("item:campfire"));

        setTexture(TEXTURES.campfire);

        itemConfiguration.name = LANGUAGES.getString("campfire");
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 15;

    }
}
