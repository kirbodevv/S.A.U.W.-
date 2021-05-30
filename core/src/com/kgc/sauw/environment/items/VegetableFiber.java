package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class VegetableFiber extends Item {
    public VegetableFiber() {
        super(ID.registeredId("item:vegetable_fiber"));

        setTexture(TEXTURES.vegetable_fiber);

        itemConfiguration.name = LANGUAGES.getString("vegetable_fiber");
        itemConfiguration.weight = 0.05f;
    }
}
