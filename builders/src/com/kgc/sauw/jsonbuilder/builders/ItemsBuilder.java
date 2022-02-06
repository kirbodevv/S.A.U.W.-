package com.kgc.sauw.jsonbuilder.builders;

import com.kgc.sauw.builder.Builder;
import com.kgc.sauw.builder.FileUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ItemsBuilder implements Builder {
    @Override
    public String build() throws IOException {
        return generateItems();
    }

    private static String generateItems() throws IOException {
        ArrayList<JSONObject> items = FileUtils.loadJsonList("%game%/json/items");
        String itemsTemplate = FileUtils.readFile("%json_builder%/templates/items.txt");
        StringBuilder code = new StringBuilder();

        for (JSONObject json : items) {
            code.append("\n");
            code.append(generateItem(json));
        }
        return itemsTemplate
                .replace("$items$", code);
    }

    private static String generateItem(JSONObject item) {
        String code = "";

        if (item.has("class")) {
            code += "\n\t\titem = new " + item.get("class") + "();";
            code += "\n\t\tItems.registry.register(item);";
        } else {
            String id = item.getString("id");
            JSONObject itemConfiguration = item.getJSONObject("itemConfiguration");
            String texture = item.getString("texture");
            int maxCount = itemConfiguration.getInt("maxCount");
            float weight = itemConfiguration.getFloat("weight");
            String type = itemConfiguration.getString("type");
            String creativeCategory = null;
            if (itemConfiguration.has("creative_category"))
                creativeCategory = itemConfiguration.getString("creative_category");

            if (type.equals("INSTRUMENT"))
                code += "\n\t\titem = new InstrumentItem();";
            else
                code += "\n\t\titem = new Item();";

            code += "\n\t\titemConfiguration = new ItemConfiguration();";
            code += "\n\t\titem.setTexture(\"" + texture + "\");";
            code += "\n\t\titemConfiguration.maxCount = " + maxCount + ";";
            code += "\n\t\titemConfiguration.weight = " + weight + "f;";
            code += "\n\t\titemConfiguration.type = Type." + type + ";";
            if (creativeCategory != null)
                code += "\n\t\titemConfiguration.creativeCategory = \"" + creativeCategory + "\";";
            if (itemConfiguration.has("maxDamage"))
                code += "\n\t\titemConfiguration.maxDamage = " + itemConfiguration.getInt("maxDamage") + ";";
            switch (type) {
                case "BLOCK_ITEM":
                    code += "\n\t\titemConfiguration.stringBlockId = \"" + itemConfiguration.getString("blockId") + "\";";
                    break;
                case "INSTRUMENT":
                    code += "\n\t\titemConfiguration.instrumentType = InstrumentItem.Type." + itemConfiguration.getString("instrumentType") + ";";
                    break;
                case "FOOD":
                    code += "\n\t\titemConfiguration.foodScore = " + itemConfiguration.getInt("foodScore") + ";";
                    break;
            }

            code += "\n\t\titem.setItemConfiguration(itemConfiguration);";
            code += "\n\t\tItems.registry.register(item, \"sauw\", \"" + id + "\");";
        }
        return code;
    }
}
