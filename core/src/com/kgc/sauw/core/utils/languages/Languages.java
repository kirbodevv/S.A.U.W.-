package com.kgc.sauw.core.utils.languages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.config.Settings;
import org.json.JSONObject;

import java.util.HashMap;

public class Languages {
    public JSONObject langs;

    private static final HashMap<String, Language> languages = new HashMap<>();

    static {
        try {
            loadLanguage(Gdx.files.internal("lang/ru.language"), "ru");
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
    }

    public static String getString(String key) {
        try {
            return languages.get(Settings.lang).getString(key);
        } catch (Exception exception) {
            Gdx.app.log("LangError", "NotFoundString : \"" + key + "\"");
            return null;
        }

    }

    public static void loadLanguage(FileHandle file, String langCode) {
        String[] lang = file.readString("UTF-8").split("\n");
        for (String string : lang) {
            String[] s = string.split(":");
            getLanguage(langCode).putString(s[0], s[1]);
        }
    }

    private static Language getLanguage(String langCode) {
        if (languages.containsKey(langCode)) return languages.get(langCode);
        else {
            Language language = new Language();
            languages.put(langCode, language);
            return language;
        }
    }
}
