package com.kgc.sauw.core.item;

import com.kgc.sauw.game.environment.Items;
import com.kgc.sauw.game.items.InstrumentItem;

public class ItemConfiguration {
    public int id;
    public String name;
    public int maxCount;
    public int maxDamage;
    public Items.Type type;
    public int blockId;
    public InstrumentItem.Type instrumentType;
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
