package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class IronIngot extends Item {
    public IronIngot() {
        super(ID.registeredId("item:iron_ingot"));

        setTexture(TEXTURES.iron_ingot);

        itemConfiguration.name = LANGUAGES.getString("iron_ingot");
        itemConfiguration.weight = 1f;

    }
}
