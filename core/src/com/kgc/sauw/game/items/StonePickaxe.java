package com.kgc.sauw.game.items;

import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class StonePickaxe extends InstrumentItem {
    public StonePickaxe() {
        super(ID.registeredId("item:stone_pickaxe"), 128, Type.PICKAXE);

        setTexture(Resource.getTexture("Items/stone_pickaxe.png"));

        itemConfiguration.name = Languages.getString("stone_pickaxe");
        itemConfiguration.weight = 5.75f;
    }
}
