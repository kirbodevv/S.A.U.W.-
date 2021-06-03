package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import org.json.JSONObject;

import static com.kgc.sauw.config.Settings.SETTINGS;

public class Languages {
    public static final Languages LANGUAGES;

    static {
        LANGUAGES = new Languages();
    }

    public JSONObject langs;

    public Languages() {
        try {
            langs = new JSONObject(Gdx.files.internal("json/langs.json").readString("UTF-8"));
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
