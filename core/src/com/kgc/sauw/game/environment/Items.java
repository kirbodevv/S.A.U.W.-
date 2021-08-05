package com.kgc.sauw.game.environment;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.item.ItemConfiguration;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.*;
import com.kgc.sauw.mods.ModResources;
import org.json.JSONObject;

import java.util.ArrayList;

public class Items {
    public enum Type {
        ITEM, BLOCK_ITEM, INSTRUMENT, FOOD, VOID
    }

    public ArrayList<Item> items = new ArrayList<>();

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
        addItem(new IronPlate());
        addItem(new Torch());
    }

    public void tick() {
        for (Item item : items) {
            item.tick();
        }
    }

    public void addItemsFromMod(FileHandle dir, ModResources resources) {
        FileHandle[] files = dir.list();
        for (FileHandle file : files) {
            if (file.isDirectory()) {
                addItemsFromMod(file, resources);
            } else {
                addItemFromJson(new JSONObject(file.readString()), resources);
            }
        }
    }

    private void addItemFromJson(JSONObject json, ModResources resources) {
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
            itemConfiguration.blockId = itemConfigurationJson.getInt("blockId");
        if (itemConfiguration.type == Type.INSTRUMENT)
            itemConfiguration.instrumentType = InstrumentItem.Type.valueOf(itemConfigurationJson.getString("instrumentType"));
        if (itemConfiguration.type == Type.FOOD)
            itemConfiguration.foodScore = itemConfigurationJson.getInt("foodScore");

        item.setItemConfiguration(itemConfiguration);

        addItem(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItemById(String id) {
        return getItemById(ID.get(id));
    }

    public Item getItemById(int id) {
        for (Item item : items) {
            if (item.id == id) {
                return item;
            }
        }
        return getItemById(0);
    }
}