package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Rope extends Item {
    public Rope() {
        super(ID.registeredId("item:rope"));

        setTexture(Resource.getTexture("Items/rope.png"));

        itemConfiguration.name = Languages.getString("rope");
    }
}
