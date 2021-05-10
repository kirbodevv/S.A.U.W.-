package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

public class VoidItem extends Item {
    public VoidItem() {
        super(ID.registeredId("item:void"));
        this.t = null;
        ItemConfiguration.name = null;
        ItemConfiguration.weight = 0f;
        ItemConfiguration.type = -1;
        ItemConfiguration.maxCount = -1;
        ItemConfiguration.maxData = -1;
    }
}
