package com.kgc.sauw.core.mod;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ModResources {
    private final HashMap<String, Texture> textures = new HashMap<>();

    public void loadResources(FileHandle resFolder) {
        FileHandle[] files = resFolder.list();
        for (FileHandle file : files) {
            if (file.isDirectory()) {
                loadResources(file);
            } else if (file.name().endsWith(".png")) {
                textures.put(file.name(), new Texture(file));
            }
        }
    }

    public Object get(String fileName) {
        if (fileName.endsWith(".png")) return textures.get(fileName);
        return null;
    }

    public void dispose() {
        Texture[] texturesA = (Texture[]) textures.values().toArray();
        for (Texture texture : texturesA) texture.dispose();
    }
}
