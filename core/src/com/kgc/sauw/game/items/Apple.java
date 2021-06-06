package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.environment.Items;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

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
