package com.kgc.sauw.core.utils.languages;

import java.util.HashMap;

public class Language {
    private final HashMap<String, String> strings = new HashMap<>();

    public String getString(String string) {
        return strings.get(string);
    }

    public void putString(String key, String string) {
        strings.put(key, string);
    }

    public boolean has(String key) {
        return strings.containsKey(key);
    }
}
