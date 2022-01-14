package com.kgc.sauw.core.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class Settings {
    @Parameter(jsonKey = "debug")
    public static boolean debugMode = true;

    @Parameter(jsonKey = "debug_renderer")
    public static boolean debugRenderer = false;

    @Parameter(jsonKey = "language")
    public static String lang = "en";

    @Parameter(jsonKey = "auto_pickup")
    public static boolean autoPickup = false;

    @Parameter(jsonKey = "music_volume")
    public static int musicVolume = 70;

    @Parameter(jsonKey = "use_console")
    public static boolean useConsole = false;

    private static JSONObject settings;

    public static void saveSettings() {
        for (Field field : Settings.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                try {
                    settings.put(field.getAnnotation(Parameter.class).jsonKey(),
                            field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        FileHandle settingsFile = Gdx.files.external("S.A.U.W./User/settings.json");
        settingsFile.writeString(settings.toString(), false);
    }

    public static void loadSettings() {
        FileHandle settingsFile = Gdx.files.external("S.A.U.W./User/settings.json");
        String result = settingsFile.readString();
        settings = new JSONObject(result);
        for (Field field : Settings.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                try {
                    field.set(null, settings.get(field.getAnnotation(Parameter.class).jsonKey()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
