package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.item.Type;

public class Chest extends Item {
    public Chest() {
        super(ID.registeredId("item:chest"));

        setTexture(Resource.getTexture("Blocks/chest.png"));

        itemConfiguration.type = Type.BLOCK_ITEM;
        itemConfiguration.blockId = 5;
    }
}
