package com.kgc.sauw.config;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;
import com.badlogic.gdx.Gdx;
import java.io.FileWriter;

public class Settings {
	private String result = "";
	//<settings>
	public boolean debugMode = true;
	public boolean debugRenderer = false;
	public String lang = "en";
	public boolean autopickup = false;
	public int consoleTextColorRed;
	public int consoleTextColorGreen;
	public int consoleTextColorBlue;
	public int musicVolume = 70;
	public boolean useConsole = false;
	//</settings>
	JSONObject settings;
	public void saveSettings() {
		try {
			this.settings.put("debug", debugMode);
			this.settings.put("debugRenderer", debugRenderer);
			this.settings.put("lang", lang);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("R", consoleTextColorRed);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("G", consoleTextColorGreen);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("B", consoleTextColorBlue);
			this.settings.getJSONObject("game").put("autopickup", autopickup);
			this.settings.getJSONObject("game").put("useConsole", useConsole);
			this.settings.getJSONObject("sound").put("music", musicVolume);
			FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
			settings.writeString(this.settings.toString(), false);
		} catch (Exception e) {
			Gdx.app.log("errr", e.toString());
		}
	}
	public Settings() {
		try {
			FileHandle settings = Gdx.files.external("S.A.U.W./User/settings.json");
			result = settings.readString();
			this.settings = new JSONObject(result);

            //<settings load>
			debugMode = this.settings.getBoolean("debug");
			debugRenderer = this.settings.getBoolean("debugRenderer");
			lang = this.settings.getString("lang");
			consoleTextColorRed = this.settings.getJSONObject("console").getJSONObject("textColor").getInt("R");
			consoleTextColorGreen = this.settings.getJSONObject("console").getJSONObject("textColor").getInt("G");
			consoleTextColorBlue = this.settings.getJSONObject("console").getJSONObject("textColor").getInt("B");
			autopickup = this.settings.getJSONObject("game").getBoolean("autopickup");
			useConsole = this.settings.getJSONObject("game").getBoolean("useConsole");
			musicVolume = this.settings.getJSONObject("sound").getInt("music");
			//</settings load>
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		}
	}
}
