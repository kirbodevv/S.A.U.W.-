package com.kgc.sauw.core.item;

import com.kgc.sauw.core.registry.Registry;

public class Items extends Registry<Item> {
    public static final Items INSTANCE = new Items();

    public static void tick() {
        for (Item item : INSTANCE.objects) {
            item.tick();
        }
    }

    @Override
    public String getIDGroup() {
        return "item";
    }
}
