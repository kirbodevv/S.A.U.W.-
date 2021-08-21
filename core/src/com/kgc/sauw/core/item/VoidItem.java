package com.kgc.sauw.core.item;

import com.kgc.sauw.core.utils.ID;

public class VoidItem extends Item {
    public VoidItem() {
        super(ID.registeredId("item:void", 0));

        itemConfiguration.name = null;
        itemConfiguration.weight = 0f;
        itemConfiguration.type = Type.VOID;
        itemConfiguration.maxCount = -1;
        itemConfiguration.maxDamage = -1;
    }
}
