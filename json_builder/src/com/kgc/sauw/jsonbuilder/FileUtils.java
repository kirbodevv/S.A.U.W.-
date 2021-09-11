package com.kgc.sauw.jsonbuilder;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FileUtils {
    public static HashMap<String, String> variables = new HashMap<>();

    public static void putVar(String varName, String path) {
        variables.put(varName, path);
    }

    public static File getFile(String path) {
        if (path.matches(".*%.+%.*")) {
            for (String varName : variables.keySet()) {
                path = path.replace("%" + varName + "%", variables.get(varName));
            }
        }
        return new File(path);
    }

    public static String readFile(String path) throws IOException {
        return readFile(getFile(path));
    }

    public static String readFile(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        return result.toString();
    }
    public static void writeString(File file, String string) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(string);
        writer.close();
    }

    public static ArrayList<JSONObject> loadJsonList(String dirPath) throws IOException {
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        File folder = getFile(dirPath);
        File listFile = new File(folder, folder.getName() + ".list");
        String[] filesList = Objects.requireNonNull(readFile(listFile)).split("\n");
        for (String file : filesList) {
            jsonObjects.add(new JSONObject(readFile(folder + "/" + file + ".json")));
        }
        return jsonObjects;
    }
}
