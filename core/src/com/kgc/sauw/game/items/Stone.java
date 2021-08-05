package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Stone extends Item {
    public Stone() {
        super(ID.registeredId("item:stone"));

        setTexture(Resource.getTexture("Items/stone.png"));

        itemConfiguration.weight = 0.15f;
        itemConfiguration.name = Languages.getString("stone");
    }
}
