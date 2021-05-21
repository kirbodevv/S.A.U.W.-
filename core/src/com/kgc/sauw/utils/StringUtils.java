package com.kgc.sauw.utils;

import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class StringUtils {
    public static String getString(String string) {
        String result = "";
        if (string.contains("%Language")) {
            result = LANGUAGES.getString(string.substring(10));
        } else {
            result = string;
        }
        if(result != null) return result;
        else return "";
    }
}
