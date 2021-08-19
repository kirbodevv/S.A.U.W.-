package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.core.item.Type;

public class Furnace extends Item {
    public Furnace() {
        super(ID.registeredId("item:furnace"));

        setTexture(Resource.getTexture("Items/furnace.png"));

        itemConfiguration.name = Languages.getString("furnace");
        itemConfiguration.type = Type.BLOCK_ITEM;
        itemConfiguration.blockId = 11;

    }
}
