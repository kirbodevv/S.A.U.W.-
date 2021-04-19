package com.kgc.sauw.environment.items;

public class NullItem extends Item {
    public NullItem() {
        super(0);
        this.t = null;
        ItemConfiguration.name = null;
        ItemConfiguration.weight = 0f;
        ItemConfiguration.type = -1;
        ItemConfiguration.maxCount = -1;
        ItemConfiguration.maxData = -1;
    }
}
