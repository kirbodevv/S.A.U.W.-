package com.kgc.sauw.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.items.InstrumentItem;
import com.kgc.sauw.mods.ModResources;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsArray {
    public final ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void addItemFromJson(JSONObject json, ModResources resources) {
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
}
