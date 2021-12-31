package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class Variables {
    private static final HashMap<String, String> variables = new HashMap<>();

    public static void putVariable(String key, String value) {
        variables.put(key, value);
        Gdx.app.log("Variables", "Variable \"" + key + "\" is set to \"" + value + "\"");
    }

    public static String getVariable(String key) {
        return variables.get(key);
    }
}
