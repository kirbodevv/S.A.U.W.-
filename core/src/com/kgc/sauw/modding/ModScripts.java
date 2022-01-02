package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

public class ModScripts {
    private final HashMap<String, String> scripts = new HashMap<>();

    public ModScripts(FileHandle scriptsDir) {
        FileHandle[] files = scriptsDir.list();
        for (FileHandle file : files) {
            if (file.name().endsWith(".js")) {
                scripts.put(file.nameWithoutExtension(), file.readString());
            }
        }
    }

    public String getScript(String key) {
        return scripts.get(key);
    }
}
