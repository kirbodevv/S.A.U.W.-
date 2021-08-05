package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.environment.Items;

public class VoidItem extends Item {
    public VoidItem() {
        super(ID.registeredId("item:void"));

        itemConfiguration.name = null;
        itemConfiguration.weight = 0f;
        itemConfiguration.type = Items.Type.VOID;
        itemConfiguration.maxCount = -1;
        itemConfiguration.maxDamage = -1;
    }
}
