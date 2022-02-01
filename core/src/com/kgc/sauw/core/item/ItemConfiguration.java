package com.kgc.sauw.core.item;

public class ItemConfiguration {
    public int maxCount;
    public int maxDamage;
    public Type type;
    public String stringBlockId = null;
    public InstrumentItem.Type instrumentType;
    public int foodScore;
    public float weight;
    public String creativeCategory = null;

    public ItemConfiguration() {
        this.weight = 0.01f;
        this.maxCount = 64;
        this.maxDamage = 0;
        this.type = Type.ITEM;
    }
}
