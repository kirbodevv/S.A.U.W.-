package com.kgc.sauw.core.environment.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.mod.ModResources;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Items {
    private static final ArrayList<Item> ITEMS = new ArrayList<>();

    public static void tick() {
        for (Item item : ITEMS) {
            item.tick();
        }
    }

    public static Item getItemById(String id) {
        return getItemById(SAUW.getId(id));
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
        Item item = new Item(json.getString("id"));
        ItemConfiguration itemConfiguration = new ItemConfiguration(item.id);

        item.setTexture((Texture) resources.get(json.getString("texture")));

        JSONObject itemConfigurationJson = json.getJSONObject("itemConfiguration");

        item.name = itemConfigurationJson.getString("name");
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

        defineItem(item);
    }

    public static void defineItem(Item item) {
        ITEMS.add(item);
        Gdx.app.log("Items", "defined item, with id " + item.getStringId() + ", item name : \"" + item.getDefaultName() + "\"");
    }
}
