package com.kgc.sauw.modding.json_data;

import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.item.ItemConfiguration;
import com.kgc.sauw.core.item.Type;
import com.kgc.sauw.core.resource.Resource;
import org.json.JSONObject;

public class JSONItem implements JSONData<Item> {
    public String id;
    public String icon;
    public int maxCount;
    public float weight;
    public Type type;
    public String creativeCategory;
    //Instrument
    public InstrumentItem.Type instrumentType;

    //Food
    public int foodScore;

    //Block_item
    public String blockId;

    @Override
    public void parse(JSONObject json) {
        JSONObject configuration = json.getJSONObject("configuration");
        id = json.getString("id");
        icon = json.getString("icon");
        maxCount = configuration.getInt("max_count");
        weight = (float) configuration.getDouble("weight");
        type = Type.valueOf(configuration.getString("type"));

        if (type == Type.INSTRUMENT)
            instrumentType = InstrumentItem.Type.valueOf(configuration.getString("instrument_type"));

        if (type == Type.FOOD)
            foodScore = configuration.getInt("food_score");

        if (type == Type.BLOCK_ITEM)
            blockId = configuration.getString("block_id");

        if (configuration.has("creative_category"))
            creativeCategory = configuration.getString("creative_category");
    }

    @Override
    public Item toObject() {
        Item item = new Item();
        ItemConfiguration itemConfiguration = new ItemConfiguration();

        item.setTexture(Resource.getTexture(icon));
        itemConfiguration.maxCount = maxCount;
        itemConfiguration.weight = weight;
        itemConfiguration.type = type;

        if (type == Type.INSTRUMENT) {
            itemConfiguration.instrumentType = instrumentType;
        }
        if (type == Type.FOOD) {
            itemConfiguration.foodScore = foodScore;
        }
        if (type == Type.BLOCK_ITEM) {
            itemConfiguration.stringBlockId = blockId;
        }

        item.setItemConfiguration(itemConfiguration);
        return item;
    }
}
