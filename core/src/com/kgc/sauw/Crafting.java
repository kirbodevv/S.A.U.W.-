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
		addCraftsFromFile(Gdx.files.internal("json/Crafts/Instruments.json"));
		addCraftsFromFile(Gdx.files.internal("json/Crafts/Ingredients.json"));
	}
	public ArrayList<craft> crafts = new ArrayList<craft>();
	public void addCraft(craft craft){
		crafts.add(craft);
	}
	public void addCraft(int[] result, int[][] ingr){
		this.addCraft(new craft(result, ingr));
	}
	public void addCraftsFromFile(FileHandle file){
		try {
			JSONArray crafts = new JSONArray(file.readString());
			for (int i = 0; i < crafts.length(); i++) {
				int[] result;
				int[][] ingr;
				JSONObject craft = crafts.getJSONObject(i);
				JSONObject resultA = craft.getJSONObject("result");
				JSONArray ingrA = craft.getJSONArray("ingredients");
				result = new int[3];
				ingr = new int[ingrA.length()][3];

				result[0] = resultA.getInt("id");
				result[1] = resultA.getInt("count");
				result[2] = resultA.getInt("data");
				Gdx.app.log("CraftID", result[0] + "");
				for (int j = 0; j < ingrA.length(); j++) {
					ingr[j][0] = ingrA.getJSONObject(j).getInt("id");
					ingr[j][1] = ingrA.getJSONObject(j).getInt("count");
					ingr[j][2] = ingrA.getJSONObject(j).getInt("data");
				}
				this.addCraft(new Crafting.craft(result, ingr));
			}
		} catch (Exception e) {
			Gdx.app.log("ModAPI_CreateCraftError", e.toString());
		}
	}
	//Для добавления крафтов из папки необходимо чтобы там был файл "Crafts" в котором написаны названия файлов
	public void addCraftsFromDirectory(FileHandle craftFolder) {
		try {
			String[] craftFiles = craftFolder.child("Crafts").readString().split("\\n");
			for (String file : craftFiles) {
				JSONObject craft = new JSONObject(craftFolder.child(file).readString());
				int[] result;
				int[][] ingr;
				JSONObject resultA = craft.getJSONObject("result");
				JSONArray ingrA = craft.getJSONArray("ingredients");
				result = new int[3];
				ingr = new int[ingrA.length()][3];

				result[0] = resultA.getInt("id");
				result[1] = resultA.getInt("count");
				result[2] = resultA.getInt("data");
				Gdx.app.log("CraftID", result[0] + "");
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
