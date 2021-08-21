package com.kgc.sauw.jsonbuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JsonBuilder {
    public static void build(String coreDir, ArrayList<JSONObject> items, String head, String end) {
        File outFile = new File(coreDir, "src/com/kgc/sauw/game/GeneratedJson.java");
        try {
            FileWriter writer = new FileWriter(outFile);

            writer.write(head);
            writer.write(generateItems(items));
            writer.write(end);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateItems(ArrayList<JSONObject> items) {
        StringBuilder code = new StringBuilder();

        for (JSONObject json : items) {
            code.append(generateItem(json));
            code.append("\n");
        }
        code.append("\n");
        return code.toString();
    }

    private static String generateItem(JSONObject item) {
        JSONObject itemConfiguration = item.getJSONObject("itemConfiguration");

        String id = item.getString("id");
        String texture = item.getString("texture");
        String name = itemConfiguration.getString("name");
        int maxCount = itemConfiguration.getInt("maxCount");
        float weight = itemConfiguration.getFloat("weight");
        String type = itemConfiguration.getString("type");


        String code = "";
        if (type.equals("INSTRUMENT"))
            code += "\n\t\titem = new InstrumentItem(\"" + id + "\");";
        else
            code += "\n\t\titem = new Item(\"" + id + "\");";

        code += "\n\t\titemConfiguration = new ItemConfiguration(\"" + id + "\");";
        code += "\n\t\titem.setTexture(\"" + texture + "\");";
        code += "\n\t\titemConfiguration.name = StringUtils.getString(\"" + name + "\");";
        code += "\n\t\titemConfiguration.maxCount = " + maxCount + ";";
        code += "\n\t\titemConfiguration.weight = " + weight + "f;";
        code += "\n\t\titemConfiguration.type = Type." + type + ";";

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
        code += "\n\t\tItems.addItem(item);";

        return code;
    }
}
