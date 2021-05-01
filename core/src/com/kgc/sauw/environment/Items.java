package com.kgc.sauw.environment;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.environment.items.*;

import java.util.ArrayList;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Items {
    public static class Type {
        public static final byte ITEM = 0;
        public static final byte BLOCKITEM = 1;
        public static final byte INSTRUMENT = 2;
        public static final byte FOOD = 3;
    }

    public ArrayList<Item> ITEMS = new ArrayList<Item>();

    public Items() {
        addItem(new NullItem());
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
    }

    public void addItem(Item item) {
        ITEMS.add(item);
    }

    public int getFirstFreeId() {
        int ii = 1;
        for (Item i : ITEMS) {
            if (ii != i.id) return ii;
            ii++;
        }
        return ITEMS.size() + 1;
    }

    public Texture getTextureById(int id) {
        for (Item item : ITEMS) {
            if (item.id == id) {
                return item.t;
            }
        }
        return TEXTURES.undf;
    }

    public int getMaxCountById(int id) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).id == id) {
                return ITEMS.get(i).getItemConfiguration().maxCount;
            }
        }
        return -1;
    }

    public int getMaxDataById(int id) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).id == id) {
                return ITEMS.get(i).getItemConfiguration().maxData;
            }
        }
        return -1;
    }

    public Item getItemById(int id) {
        for (Item item : ITEMS) {
            if (item.id == id) {
                return item;
            }
        }
        return getItemById(0);
    }

    public String getNameById(int id) {
        for (Item item : ITEMS) {
            if (item.id == id) {
                return item.getItemConfiguration().name;
            }
        }
        return "Нет имени";
    }

}
