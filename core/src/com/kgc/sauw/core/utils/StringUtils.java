package com.kgc.sauw.core.utils;

import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

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
