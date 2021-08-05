package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.game.environment.Items;

public class Sapling extends Item {
    public Sapling() {
        super(ID.registeredId("item:sapling"));

        setTexture(Resource.getTexture("Items/sapling.png"));

        itemConfiguration.name = Languages.getString("sapling");
        itemConfiguration.weight = 0.1f;
        itemConfiguration.type = Items.Type.BLOCK_ITEM;
        itemConfiguration.blockId = 13;
    }
}
