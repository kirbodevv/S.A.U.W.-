package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

public class VoidItem extends Item {
    public VoidItem() {
        super(ID.registeredId("item:void"));

        itemConfiguration.name = null;
        itemConfiguration.weight = 0f;
        itemConfiguration.type = -1;
        itemConfiguration.maxCount = -1;
        itemConfiguration.maxData = -1;
    }
}
