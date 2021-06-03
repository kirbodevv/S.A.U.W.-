package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class VegetableFiber extends Item {
    public VegetableFiber() {
        super(ID.registeredId("item:vegetable_fiber"));

        setTexture(TEXTURES.vegetable_fiber);

        itemConfiguration.name = LANGUAGES.getString("vegetable_fiber");
        itemConfiguration.weight = 0.05f;
    }
}
