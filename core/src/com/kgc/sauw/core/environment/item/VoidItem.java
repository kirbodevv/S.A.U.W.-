package com.kgc.sauw.core.environment.item;

public class VoidItem extends Item {
    public VoidItem() {
        super(0, "item:void");

        itemConfiguration.weight = 0f;
        itemConfiguration.type = Type.VOID;
        itemConfiguration.maxCount = -1;
        itemConfiguration.maxDamage = -1;
    }
}
