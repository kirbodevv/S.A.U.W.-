package com.kgc.sauwlauncher.launcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Settings {

    private static LinkedHashMap<String, String> settings = new LinkedHashMap<>();

    public static void save() throws IOException {
        FileWriter fileWriter = new FileWriter(Files.launcherSettings);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> stringStringEntry : settings.entrySet()) {
            builder.append(stringStringEntry.getKey()).append(":").append(stringStringEntry.getValue()).append("\n");
        }
        fileWriter.write(builder.toString());
        fileWriter.close();
    }

    public static void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Files.launcherSettings));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] setting = line.split(":");
                settings.put(setting[0], setting[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        return settings.get(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(settings.get(key));
    }

    public static float getFloat(String key) {
        return Float.parseFloat(settings.get(key));
    }

    public static void set(String key, String value) {
        settings.put(key, value);
    }
}
