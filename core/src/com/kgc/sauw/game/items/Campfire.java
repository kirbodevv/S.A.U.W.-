package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.core.item.Type;

public class Campfire extends Item {
    public Campfire() {
        super(ID.registeredId("item:campfire"));

        setTexture(Resource.getTexture("Blocks/campfire.png"));

        itemConfiguration.name = Languages.getString("campfire");
        itemConfiguration.type = Type.BLOCK_ITEM;
        itemConfiguration.blockId = 15;

    }
}
