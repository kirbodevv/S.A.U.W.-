package com.kgc.sauw.core.utils;

import com.kgc.sauw.core.utils.languages.Languages;

public class StringUtils {
    public static String getString(String string) {
        String result = string.contains("%Language") ? Languages.getString(string.substring(10)) : string;
        if(result != null) return result;
        else return "";
    }
}
