package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Handsaw extends Item {
    public Handsaw() {
        super(ID.registeredId("item:handsaw"));

        setTexture(Resource.getTexture("Items/handsaw.png"));

        itemConfiguration.weight = 1f;
        itemConfiguration.maxDamage = 100;
        itemConfiguration.name = Languages.getString("handsaw");
    }
}
