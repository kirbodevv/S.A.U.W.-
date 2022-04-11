package com.kgc.sauw.core.utils;

import java.util.HashMap;

public class Emoji {
    public static final String moyai = "\u0300";
    public static final String laugh = "\u0301";
    public static final String peanut = "\u0302";

    public static final HashMap<String, String> emoji = new HashMap<>();

    static {
        emoji.put("moyai", moyai);
        emoji.put("laugh", laugh);
        emoji.put("peanut", peanut);
    }

    public static String replaceEmojis(String raw) {
        String result = raw;
        for (String key : emoji.keySet()) {
            if (raw.contains(":" + key + ":")) {
                result = result.replace(":" + key + ":", emoji.get(key));
            }
        }
        return result;
    }
}
