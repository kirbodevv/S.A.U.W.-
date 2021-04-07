package com.kgc.sauw;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.config.Settings;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.utils.Languages;
import org.json.JSONArray;

public class Achievements {
    public static class achievement {
		public String id;
		public String title;
		public String text;
		public Texture img;
		public int giveCoins;
		public boolean wasGave = false;
		public achievement(String id, String title, String text, Texture img, int giveCoins) {
			this.id = id;
			this.title = title;
			this.text = text;
			this.img = img;
			this.giveCoins = giveCoins;
		}	
	}
	public Achievements(Languages l) {
		try {
			JSONArray achievements = new JSONArray(Gdx.files.internal("json/achivements.json").readString());
		    for (int i = 0; i < achievements.length(); i++) {
				addAchievement(new achievement(achievements.getJSONObject(i).getString("id"), 
											   l.getString(achievements.getJSONObject(i).getString("title")), 
											   l.getString(achievements.getJSONObject(i).getString("txt")),
											   new Texture(Gdx.files.internal(achievements.getJSONObject(i).getString("texture"))),
											   achievements.getJSONObject(i).getInt("giveCoins")));
			}
		} catch (Exception e) {

		}
	}
	private ArrayList<achievement> achievements = new ArrayList<achievement>();
	public void addAchievement(achievement ac) {
		achievements.add(ac);
	}
	public void giveAchievment(Player pl, String id, GameInterface GI, Settings s) {
		if (!s.useConsole) {
			for (achievement a : achievements) {
				if (a.id.equals(id)) {
					if (!a.wasGave) {
						try {
							int WIDTH = Gdx.graphics.getWidth();
							int HEIGHT = Gdx.graphics.getHeight();
							pl.data.put("SAUW_Coins", pl.data.getInt("SAUW_Coins") + a.giveCoins);
							pl.saveData();
							GI.showNotification(a.title, a.text, a.img, 6);
							a.wasGave = true;
						} catch (Exception e) {

						}
					}
				}
			}
		}
	}
}
