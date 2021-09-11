package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Resource {
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static final HashMap<String, Music> music = new HashMap<>();

    public static Texture getTexture(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            texture = new Texture(Gdx.files.internal(path));
            textures.put(path, texture);
            Gdx.app.log("Resource loader", "texture \"" + path + "\" loaded");
        }
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
        for(Texture texture : textures.values()) texture.dispose();
        for(Music music : music.values()) music.dispose();
    }
}
