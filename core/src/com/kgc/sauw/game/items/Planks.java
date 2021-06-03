package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Planks extends Item {

    public Planks() {
        super(ID.registeredId("item:planks"));

        setTexture(TEXTURES.planks);

        itemConfiguration.name = LANGUAGES.getString("planks");
        itemConfiguration.weight = 0.55f;
    }
}
