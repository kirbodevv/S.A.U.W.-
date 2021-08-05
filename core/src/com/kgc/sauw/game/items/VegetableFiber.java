package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class VegetableFiber extends Item {
    public VegetableFiber() {
        super(ID.registeredId("item:vegetable_fiber"));

        setTexture(Resource.getTexture("Items/vegetable_fiber.png"));

        itemConfiguration.name = Languages.getString("vegetable_fiber");
        itemConfiguration.weight = 0.05f;
    }
}
