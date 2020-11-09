package com.KGC.SAUW;
import java.io.File;
import android.os.Environment;
import java.io.FileReader;
import java.util.Scanner;
import org.json.JSONObject;
import com.badlogic.gdx.Gdx;
import java.io.FileWriter;

public class settings {
	private String result = "";
	//<settings>
	public boolean debugMode = true;
	public String lang = "de";
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
			this.settings.put("lang", lang);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("R", consoleTextColorRed);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("G", consoleTextColorGreen);
			this.settings.getJSONObject("console").getJSONObject("textColor").put("B", consoleTextColorBlue);
			this.settings.getJSONObject("game").put("autopickup", autopickup);
			this.settings.getJSONObject("game").put("useConsole", useConsole);
			this.settings.getJSONObject("sound").put("music", musicVolume);
			File settings = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./User/settings.json");
			FileWriter s = new FileWriter(settings.toString());
			s.write(this.settings.toString());
			s.close();
		} catch (Exception e) {
			Gdx.app.log("errr", e.toString());
		}
	}
	public settings() {
		try {
			File settings = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./User/settings.json");
			FileReader s = new FileReader(settings.toString());
			Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				result += scanner.nextLine();
			}
			s.close();
			this.settings = new JSONObject(result);

            //<settings load>
			debugMode = this.settings.getBoolean("debug");
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
