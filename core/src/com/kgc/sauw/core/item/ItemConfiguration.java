package com.kgc.sauw.core.item;

import com.kgc.sauw.core.environment.Items;

public class ItemConfiguration {
    public int id;
    public String StringId;
    public String name;
    public int maxCount;
    public int maxDamage;
    public int type;
    public int blockId;
    public int instrumentType;
    public int foodScore;
    public float weight;

    public ItemConfiguration(int id) {
        this.id = id;
        this.weight = 0.01f;
        this.maxCount = 64;
        this.maxDamage = 0;
        this.type = Items.Type.ITEM;
    }
}
