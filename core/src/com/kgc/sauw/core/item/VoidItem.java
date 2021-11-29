package com.kgc.sauw.core.item;

public class VoidItem extends Item {
    public VoidItem() {
        itemConfiguration.weight = 0f;
        itemConfiguration.type = Type.VOID;
        itemConfiguration.maxCount = -1;
        itemConfiguration.maxDamage = -1;
    }
}
