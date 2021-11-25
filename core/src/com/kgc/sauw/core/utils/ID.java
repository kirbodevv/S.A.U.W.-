package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class ID {
    private static final HashMap<String, IdGroup> map = new HashMap<>();

    private static class IdGroup {
        int maxVal = 0;
        HashMap<String, Integer> idMap = null;
    }

    public static void registeredIdGroup(String key, int maxVal) {
        IdGroup idGroupMap = new IdGroup();
        idGroupMap.maxVal = maxVal;
        idGroupMap.idMap = new HashMap<>();
        map.put(key, idGroupMap);
        Gdx.app.log("ID manager", "registered id group \"" + key + "\"");
    }

    public static int registeredId(String package_, String key) {
        int iterator = 0;
        String[] keys = key.split(":");
        IdGroup idGroupMap = map.get(keys[0]);
        while (iterator < idGroupMap.maxVal) {
            if (!idGroupMap.idMap.containsValue(iterator)) {
                idGroupMap.idMap.put(package_ + ":" + keys[1], iterator);
                Gdx.app.log("ID manager", "registered string id \"" + key + "\" for package \"" + package_ + "\", with integer id " + iterator);
                return iterator;
            } else iterator++;
        }
        return 0;
    }


    public static int get(String package_, String key) {
        Integer integer;
        String[] keys = key.split(":");
        IdGroup idGroupHashMap = map.get(keys[0]);
        if (idGroupHashMap == null || (integer = idGroupHashMap.idMap.get(package_ + ":" + keys[1])) == null)
            throw new RuntimeException("Cannot found id " + key);
        return integer;
    }

    static {
        registeredIdGroup("block", 1200);
        registeredIdGroup("item", 1200);
        registeredIdGroup("entity", 1200);
        registeredIdGroup("animation", 1200);
        registeredIdGroup("animation_region", 1200);
        registeredIdGroup("particle", 1200);
        registeredIdGroup("achievements", 1200);
    }
}