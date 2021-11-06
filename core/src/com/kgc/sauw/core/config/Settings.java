package com.kgc.sauw.core.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

public class Settings {
    //<settings>
    public static boolean debugMode = true;
    public static boolean debugRenderer = false;
    public static String lang = "en";
    public static boolean autoPickup = false;
    public static int consoleTextColorRed;
    public static int consoleTextColorGreen;
    public static int consoleTextColorBlue;
    public static int musicVolume = 70;
    public static boolean useConsole = false;
    //</settings>
    private static JSONObject settings;

    public static void saveSettings() {
        settings.put("debug", debugMode);
        settings.put("debugRenderer", debugRenderer);
        settings.put("lang", lang);
        settings.getJSONObject("console").getJSONObject("textColor").put("R", consoleTextColorRed);
        settings.getJSONObject("console").getJSONObject("textColor").put("G", consoleTextColorGreen);
        settings.getJSONObject("console").getJSONObject("textColor").put("B", consoleTextColorBlue);
        settings.getJSONObject("game").put("autopickup", autoPickup);
        settings.getJSONObject("game").put("useConsole", useConsole);
        settings.getJSONObject("sound").put("music", musicVolume);
        FileHandle settingsFile = Gdx.files.external("S.A.U.W./User/settings.json");
        settingsFile.writeString(settings.toString(), false);
    }

    public static void loadSettings() {
        FileHandle settingsFile = Gdx.files.external("S.A.U.W./User/settings.json");
        String result = settingsFile.readString();
        settings = new JSONObject(result);

        //<settings load>
        debugMode = settings.getBoolean("debug");
        debugRenderer = settings.getBoolean("debugRenderer");
        lang = settings.getString("lang");
        consoleTextColorRed = settings.getJSONObject("console").getJSONObject("textColor").getInt("R");
        consoleTextColorGreen = settings.getJSONObject("console").getJSONObject("textColor").getInt("G");
        consoleTextColorBlue = settings.getJSONObject("console").getJSONObject("textColor").getInt("B");
        autoPickup = settings.getJSONObject("game").getBoolean("autopickup");
        useConsole = settings.getJSONObject("game").getBoolean("useConsole");
        musicVolume = settings.getJSONObject("sound").getInt("music");
        //</settings load>
    }
}
