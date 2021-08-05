package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class IronPlate extends Item {
    public IronPlate() {
        super(ID.registeredId("item:iron_plate"));

        setTexture(Resource.getTexture("Items/iron_plate.png"));

        itemConfiguration.name = Languages.getString("iron_plate");
        itemConfiguration.weight = 0.8f;
    }
}