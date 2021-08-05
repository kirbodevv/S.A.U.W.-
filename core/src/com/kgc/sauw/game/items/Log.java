package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Log extends Item {
    public Log() {
        super(ID.registeredId("item:log"));

        setTexture(Resource.getTexture("Items/log.png"));

        itemConfiguration.name = Languages.getString("log");
        itemConfiguration.weight = 1.25f;
    }
}
