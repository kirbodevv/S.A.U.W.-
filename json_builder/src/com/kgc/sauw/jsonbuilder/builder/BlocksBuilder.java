package com.kgc.sauw.jsonbuilder.builder;

import com.kgc.sauw.jsonbuilder.FileUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BlocksBuilder implements Builder{
    @Override
    public String build() throws IOException {
        return null;
    }

    private static String generateBlocks() throws IOException {
        ArrayList<JSONObject> blocks = FileUtils.loadJsonList("%game%/json/blocks");
        StringBuilder code = new StringBuilder();

        for (JSONObject json : blocks) {
            code.append("\n");
            code.append(generateBlock(json));
        }
        return code.toString();
    }


    private static String generateBlock(JSONObject block) {
        String code = "";

        return code;
    }
}
