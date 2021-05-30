package com.kgc.sauw.environment;

import com.kgc.sauw.environment.items.*;

import java.util.ArrayList;

public class Items {
    public static class Type {
        public static final byte ITEM = 0;
        public static final byte BLOCK_ITEM = 1;
        public static final byte INSTRUMENT = 2;
        public static final byte FOOD = 3;
    }

    public ArrayList<Item> ITEMS = new ArrayList<>();

    public Items() {
        addItem(new VoidItem());
        addItem(new Grass());
        addItem(new Apple());
        addItem(new Chest());
        addItem(new Stick());
        addItem(new Log());
        addItem(new Stone());
        addItem(new IronOre());
        addItem(new StonePickaxe());
        addItem(new StoneAxe());
        addItem(new StoneShovel());
        addItem(new Furnace());
        addItem(new Rope());
        addItem(new VegetableFiber());
        addItem(new Sapling());
        addItem(new IronIngot());
        addItem(new Campfire());
        addItem(new Hammer());
        addItem(new Handsaw());
        addItem(new Planks());
    }

    public void addItem(Item item) {
        ITEMS.add(item);
    }

    public Item getItemById(int id) {
        for (Item item : ITEMS) {
            if (item.id == id) {
                return item;
            }
        }
        return getItemById(0);
    }
}