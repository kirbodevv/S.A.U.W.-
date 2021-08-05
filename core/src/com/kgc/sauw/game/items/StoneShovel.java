package com.kgc.sauw.game.items;

import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class StoneShovel extends InstrumentItem {
    public StoneShovel() {
        super(ID.registeredId("item:stone_shovel"), 128, Type.SHOVEL);

        setTexture(Resource.getTexture("Items/stone_shovel.png"));

        itemConfiguration.name = Languages.getString("stone_shovel");
        itemConfiguration.weight = 5.75f;
    }
}