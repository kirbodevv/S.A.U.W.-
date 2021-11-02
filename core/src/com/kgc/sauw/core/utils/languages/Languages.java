package com.kgc.sauw.core.utils.languages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.config.Settings;

import java.util.HashMap;

public class Languages {
    private static final HashMap<String, Language> languages = new HashMap<>();

    static {
        String[] files = Gdx.files.internal("lang/available_languages.list").readString().split("\\r?\\n");
        for (String file : files) {
            String langCode = file.split(":")[0];
            loadLanguage(Gdx.files.internal("lang").child(langCode + ".language"), langCode);
        }
    }

    public static String getString(String key) {
        try {
            if (languages.get(Settings.lang).has(key)) {
                return languages.get(Settings.lang).getString(key);
            } else {
                Gdx.app.log("Languages error", "string not found \"" + key + "\"");
                return key;
            }
        } catch (Exception e) {
            Gdx.app.log("Languages error", e.toString());
            return "error";
        }
    }

    public static void loadLanguage(FileHandle file, String langCode) {
        String[] lang = file.readString("UTF-8").split("\\r?\\n");
        for (String string : lang) {
            String[] s = string.split("=");
            get(langCode).putString(s[0], s[1]);
        }
        Gdx.app.log("Languages", "loaded language, with langcode " + langCode);
    }

    private static Language get(String langCode) {
        if (languages.containsKey(langCode)) return languages.get(langCode);
        else {
            Language language = new Language();
            languages.put(langCode, language);
            return language;
        }
    }
}
