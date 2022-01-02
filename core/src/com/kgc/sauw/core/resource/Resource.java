package com.kgc.sauw.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.utils.Variables;

import java.util.HashMap;

public class Resource {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static final HashMap<String, Music> music = new HashMap<>();

    public static Texture getTexture(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            String textureName = path;
            if (path.charAt(0) == '$') {
                textureName = path;
                path = Variables.getVariable(path.substring(1));
            }
            FileHandle file = Gdx.files.external(path);
            if (!file.exists()) file = Gdx.files.internal(path);
            texture = loadTexture(file, textureName);
        }
        return texture;
    }

    public static void putTexture(String name, Texture texture) {
        if (textures.containsKey(name))
            textures.get(name).dispose();
        textures.put(name, texture);
    }

    public static Texture loadTexture(FileHandle fileHandle, String name) {
        Texture texture = new Texture(fileHandle);
        putTexture(name, texture);
        Gdx.app.log("Resource loader", "texture \"" + fileHandle.type() + "::" + name + "\" loaded");
        return texture;
    }

    public static Music getMusic(String path) {
        Music music_ = music.get(path);
        if (music_ == null) {
            music_ = Gdx.audio.newMusic((Gdx.files.internal(path)));
            music.put(path, music_);
            Gdx.app.log("Resource loader", "music \"" + path + "\" loaded");
        }
        return music_;
    }

    public static void dispose() {
        for (Texture texture : textures.values()) texture.dispose();
        for (Music music : music.values()) music.dispose();
    }
}
