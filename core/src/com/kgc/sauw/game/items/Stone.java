package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Stone extends Item {
    public Stone() {
        super(ID.registeredId("item:stone"));

        setTexture(TEXTURES.stone_item);

        itemConfiguration.weight = 0.15f;
        itemConfiguration.name = LANGUAGES.getString("stone");
    }
}
