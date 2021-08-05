package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.game.environment.Items;

public class Apple extends Item {
    public Apple() {
        super(ID.registeredId("item:apple"));
        setTexture(Resource.getTexture("Items/apple.png"));

        itemConfiguration.weight = 0.2f;
        itemConfiguration.name = Languages.getString("apple");
        itemConfiguration.type = Items.Type.FOOD;
        itemConfiguration.foodScore = 4;
    }
}
