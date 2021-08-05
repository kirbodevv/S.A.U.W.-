package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class IronOre extends Item {
    public IronOre() {
        super(ID.registeredId("item:iron_ore"));

        setTexture(Resource.getTexture("Items/iron_ore.png"));

        itemConfiguration.name = Languages.getString("iron_ore");
        itemConfiguration.weight = 0.5f;

    }
}
