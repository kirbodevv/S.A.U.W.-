package com.kgc.sauw;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class Crafting {
    public static class craft{
		int[] result;
		int[][] ingr;
		public craft(int[] result, int[][] ingr){
			this.result = result;
			this.ingr = ingr;
		}
	}
	public Crafting(){
		addCrafts(Gdx.files.internal("json/Crafts"));
	}
	public ArrayList<craft> crafts = new ArrayList<craft>();
	public void addCraft(craft craft){
		crafts.add(craft);
		Gdx.app.log("craftAdded_ID:", craft.result[0] + "");
	}
	public void addCraft(int[] result, int[][] ingr){
		this.addCraft(new craft(result, ingr));
	}
	public void addCrafts(FileHandle craftFolder) {
		try {
			FileHandle[] craftFiles = craftFolder.list();
			for (FileHandle file : craftFiles) {
				JSONObject craft = new JSONObject(file.readString());
				int[] result;
				int[][] ingr;
				JSONObject resultA = craft.getJSONObject("result");
				JSONArray ingrA = craft.getJSONArray("ingredients");
				result = new int[3];
				ingr = new int[ingrA.length()][3];

				result[0] = resultA.getInt("id");
				result[1] = resultA.getInt("count");
				result[2] = resultA.getInt("data");

				for (int i = 0; i < ingrA.length(); i++) {
					ingr[i][0] = ingrA.getJSONObject(i).getInt("id");
					ingr[i][1] = ingrA.getJSONObject(i).getInt("count");
					ingr[i][2] = ingrA.getJSONObject(i).getInt("data");
				}

				this.addCraft(new Crafting.craft(result, ingr));
			}
		} catch (Exception e) {
			Gdx.app.log("ModAPI_CreateCraftError", e.toString());
		}
	}
}
