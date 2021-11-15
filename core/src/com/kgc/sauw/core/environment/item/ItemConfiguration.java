package com.kgc.sauw.core.environment.item;

import static com.kgc.sauw.core.GameContext.SAUW;

public class ItemConfiguration {
    public int id;
    public int maxCount;
    public int maxDamage;
    public Type type;
    public String stringBlockId = null;
    public InstrumentItem.Type instrumentType;
    public int foodScore;
    public float weight;

    public ItemConfiguration(String id) {
        this(SAUW.getId(id));
    }

    public ItemConfiguration(int id) {
        this.id = id;
        this.weight = 0.01f;
        this.maxCount = 64;
        this.maxDamage = 0;
        this.type = Type.ITEM;
    }
}
