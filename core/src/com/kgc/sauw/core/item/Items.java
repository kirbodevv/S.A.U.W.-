package com.kgc.sauw.core.item;

import com.kgc.sauw.core.registry.Registry;

public class Items {
    public static final Registry<Item> registry = new Registry<>("item");

    public static void tick() {
        for (Item item : registry.getObjects()) {
            item.tick();
        }
    }
}
