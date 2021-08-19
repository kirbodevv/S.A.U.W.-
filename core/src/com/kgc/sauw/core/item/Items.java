package com.kgc.sauw.core.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.mods.ModResources;
import org.json.JSONObject;

import java.util.ArrayList;

public class Items {
    private static final ArrayList<ItemsArray> ITEMS_ARRAYS = new ArrayList<>();

    public static void addItemsArray(ItemsArray itemsArray) {
        ITEMS_ARRAYS.add(itemsArray);
        Gdx.app.log("ITEMS", "added items array, count of items " + itemsArray.items.size());
    }

    public static void tick() {
        for (ItemsArray itemsArray : ITEMS_ARRAYS) {
            for (Item item : itemsArray.items) {
                item.tick();
            }
        }
    }

    public static Item getItemById(String id) {
        return getItemById(ID.get(id));
    }

    public static Item getItemById(int id) {
        for (ItemsArray itemsArray : ITEMS_ARRAYS) {
            for (Item item : itemsArray.items) {
                if (item.id == id) {
                    return item;
                }
            }
        }
        return getItemById(0);
    }
    public static void addItemsFromMod(FileHandle dir, ModResources resources) {
        FileHandle[] files = dir.list();
        ItemsArray itemsArray = new ItemsArray();
        for (FileHandle file : files) {
            if (file.isDirectory()) {
                addItemsFromMod(file, resources);
            } else {
                itemsArray.addItemFromJson(new JSONObject(file.readString()), resources);
            }
        }
    }
}
