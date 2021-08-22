package com.kgc.sauw.core.item;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.mods.ModResources;
import org.json.JSONObject;

import java.util.ArrayList;

public class Items {
    private static final ArrayList<Item> ITEMS = new ArrayList<>();

    public static void tick() {
        for (Item item : ITEMS) {
            item.tick();
        }
    }

    public static Item getItemById(String id) {
        return getItemById(ID.get(id));
    }

    public static Item getItemById(int id) {
        for (Item item : ITEMS)
            if (item.id == id) return item;
        return getItemById(0);
    }

    public static void addItemsFromMod(FileHandle dir, ModResources resources) {
        FileHandle[] files = dir.list();
        for (FileHandle file : files) {
            if (file.isDirectory()) {
                addItemsFromMod(file, resources);
            } else {
                addItemFromJson(new JSONObject(file.readString()), resources);
            }
        }
    }

    public static void addItemFromJson(JSONObject json, ModResources resources) {
        int id = ID.registeredId(json.getString("id"));
        Item item = new Item(id);
        ItemConfiguration itemConfiguration = new ItemConfiguration(id);

        item.setTexture((Texture) resources.get(json.getString("texture")));

        JSONObject itemConfigurationJson = json.getJSONObject("itemConfiguration");

        itemConfiguration.name = itemConfigurationJson.getString("name");
        itemConfiguration.type = Type.valueOf(itemConfigurationJson.getString("type"));
        itemConfiguration.maxCount = itemConfigurationJson.getInt("maxCount");
        itemConfiguration.weight = itemConfigurationJson.getFloat("weight");

        if (itemConfigurationJson.has("maxDamage"))
            itemConfiguration.maxDamage = itemConfigurationJson.getInt("maxDamage");

        if (itemConfiguration.type == Type.BLOCK_ITEM)
            itemConfiguration.stringBlockId = itemConfigurationJson.getString("blockId");
        if (itemConfiguration.type == Type.INSTRUMENT)
            itemConfiguration.instrumentType = InstrumentItem.Type.valueOf(itemConfigurationJson.getString("instrumentType"));
        if (itemConfiguration.type == Type.FOOD)
            itemConfiguration.foodScore = itemConfigurationJson.getInt("foodScore");

        item.setItemConfiguration(itemConfiguration);

        addItem(item);
    }

    public static void addItem(Item item) {
        ITEMS.add(item);
    }
}
