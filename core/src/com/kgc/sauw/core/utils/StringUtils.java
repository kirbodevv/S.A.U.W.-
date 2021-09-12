package com.kgc.sauw.core.utils;

import com.kgc.sauw.core.utils.languages.Languages;

public class StringUtils {
    public static String getString(String string) {
        String result;
        if (string.contains("%Language")) {
            result = Languages.getString(string.substring(10));
        } else {
            result = string;
        }
        if(result != null) return result;
        else return "";
    }
}
