package com.kgc.sauw.core.utils;

import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class FileUtils {
    public static String[] loadList(FileHandle file) {
        return file.readString().split("\n");
    }

    public static void saveList(FileHandle file, ArrayList<String> list) {
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            if (!s.replaceAll("\\s+", "").equals(""))
                result.append(s).append("\n");
        }
        file.writeString(result.toString(), false);
    }

    public static void saveList(FileHandle file, String[] list) {
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            result.append(s).append("\n");
        }
        file.writeString(result.toString(), false);
    }
}
