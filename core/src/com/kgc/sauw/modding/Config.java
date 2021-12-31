package com.kgc.sauw.modding;

import org.json.JSONObject;

import java.util.Map;

public class Config {
    private final JSONObject config;

    public Config(JSONObject config) {
        this.config = config;
        if (!config.has("enabled")) config.put("enabled", true);
    }

    public Map<String, Object> toMap() {
        return config.toMap();
    }

    public void put(String key, Object o) {
        config.put(key, o);
    }

    public Object get(String key) {
        return config.get(key);
    }

    @Override
    public String toString() {
        return config.toString();
    }
}
