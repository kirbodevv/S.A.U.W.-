package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Apple extends Item {
    public Apple() {
        super(ID.registeredId("item:apple"));
        setTexture(TEXTURES.apple);

        itemConfiguration.weight = 0.2f;
        itemConfiguration.name = LANGUAGES.getString("apple");
        itemConfiguration.type = Items.Type.FOOD;
        itemConfiguration.foodScore = 4;
    }
}
