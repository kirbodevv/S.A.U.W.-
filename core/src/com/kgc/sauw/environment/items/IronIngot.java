package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class IronIngot extends Item {
    public IronIngot() {
        super(ID.registeredId("item:iron_ingot"));

        setTexture(TEXTURES.iron_ingot);

        ItemConfiguration.name = LANGUAGES.getString("iron_ingot");
        ItemConfiguration.weight = 1f;

    }
}
