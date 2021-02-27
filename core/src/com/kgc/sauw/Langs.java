package com.kgc.sauw;
import org.json.JSONObject;
import com.badlogic.gdx.Gdx;

public class Langs {
    public JSONObject langs;
	private Settings settings;
	public Langs(Settings s) {
		this.settings = s;
		try {
			langs = new JSONObject(Gdx.files.internal("json/langs.json").readString());
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		}
	}
	public String getString(String id) {
		try {
			return langs.getJSONObject(settings.lang).getString(id);
		} catch (Exception j) {
			Gdx.app.log("LangsError", "NotFoundString : \"" + id + "\"");
			return null;
		}
	}
}
