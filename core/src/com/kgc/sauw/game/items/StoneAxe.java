package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class StoneAxe extends InstrumentItem {
    public StoneAxe() {
        super(ID.registeredId("item:stone_axe"), 128, Type.AXE);

        setTexture(Resource.getTexture("Items/stone_axe.png"));
        itemConfiguration.weight = 5.75f;
        itemConfiguration.name = Languages.getString("stone_axe");

    }
}
