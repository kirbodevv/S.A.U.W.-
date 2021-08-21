package com.kgc.sauw.jsonbuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<JSONObject> items = new ArrayList<>();

        String[] filesList = readFile(args[0] + "/json/items/items.list").split("\n");

        for (String file : filesList) {
            items.add(new JSONObject(readFile(args[0] + "/json/items/" + file + ".json")));
        }

        JsonBuilder.build(args[0], items, readFile(args[1] + "/src/head.txt"), readFile(args[1] + "/src/end.txt"));
    }

    private static String readFile(String path) {
        return readFile(new File(path));
    }

    private static String readFile(File file) {
        try {
            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
