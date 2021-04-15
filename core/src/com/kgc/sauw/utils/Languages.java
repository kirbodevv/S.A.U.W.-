package com.kgc.sauw.utils;

import com.kgc.sauw.config.Settings;
import org.json.JSONObject;
import com.badlogic.gdx.Gdx;

import static com.kgc.sauw.config.Settings.SETTINGS;

public class Languages {
    public static final Languages LANGUAGES;

    static {
        LANGUAGES = new Languages();
    }

    public JSONObject langs;

    public Languages() {
        try {
            langs = new JSONObject(Gdx.files.internal("json/langs.json").readString());
        } catch (Exception e) {
            Gdx.app.log("error", e.toString());
        }
    }

    public String getString(String id) {
        try {
            return langs.getJSONObject(SETTINGS.lang).getString(id);
        } catch (Exception j) {
            Gdx.app.log("LangsError", "NotFoundString : \"" + id + "\"");
            return null;
        }
    }
}
