package com.kgc.sauw.environment.items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class VegetableFiber extends Item {
    public VegetableFiber() {
        super(18);

        this.t = TEXTURES.vegetable_fiber;

        ItemConfiguration.name = LANGUAGES.getString("vegetable_fiber");
        ItemConfiguration.weight = 0.05f;
    }
}
