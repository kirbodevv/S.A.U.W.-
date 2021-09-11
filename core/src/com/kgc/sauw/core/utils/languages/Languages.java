package com.kgc.sauw.core.utils.languages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.config.Settings;
import com.kgc.sauw.game.resource.Files;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class Languages {
    private static final HashMap<String, Language> languages = new HashMap<>();

    static {
        try {
            Iterator<String> langCodes = Files.availableLanguages.keys();
            while (langCodes.hasNext()){
                String code = langCodes.next();
                loadLanguage(Gdx.files.internal("lang/" + code + ".language"), code);
            }
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
    }

    public static String getString(String key) {
        try {
            return languages.get(Settings.lang).getString(key);
        } catch (Exception exception) {
            Gdx.app.log("LangError", "NotFoundString : \"" + key + "\"");
            return "";
        }

    }

    public static void loadLanguage(FileHandle file, String langCode) {
        String[] lang = file.readString("UTF-8").split("\r\n");
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
