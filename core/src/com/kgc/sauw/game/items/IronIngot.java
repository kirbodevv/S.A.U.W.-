package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class IronIngot extends Item {
    public IronIngot() {
        super(ID.registeredId("item:iron_ingot"));

        setTexture(Resource.getTexture("Items/iron_ingot.png"));

        itemConfiguration.name = Languages.getString("iron_ingot");
        itemConfiguration.weight = 1f;

    }
}
