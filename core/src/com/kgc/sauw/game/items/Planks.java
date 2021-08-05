package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Planks extends Item {

    public Planks() {
        super(ID.registeredId("item:planks"));

        setTexture(Resource.getTexture("Items/planks.png"));

        itemConfiguration.name = Languages.getString("planks");
        itemConfiguration.weight = 0.55f;
    }
}
