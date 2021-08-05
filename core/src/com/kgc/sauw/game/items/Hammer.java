package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Hammer extends Item {
    public Hammer() {
        super(ID.registeredId("item:hammer"));
        setTexture(Resource.getTexture("Items/hammer.png"));

        itemConfiguration.name = Languages.getString("hammer");
        itemConfiguration.maxDamage = 100;
        itemConfiguration.weight = 1.25f;
    }
}
