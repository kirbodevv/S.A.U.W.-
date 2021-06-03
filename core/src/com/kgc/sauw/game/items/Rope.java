package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Rope extends Item {
    public Rope() {
        super(ID.registeredId("item:rope"));

        setTexture(TEXTURES.rope);

        itemConfiguration.name = LANGUAGES.getString("rope");
    }
}
