package com.kgc.sauw.core.utils;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.config.Settings;
import org.json.JSONObject;

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
            return langs.getJSONObject(Settings.lang).getString(id);
        } catch (Exception j) {
            Gdx.app.log("LangsError", "NotFoundString : \"" + id + "\"");
            return null;
        }
    }
}
