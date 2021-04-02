package com.kgc.sauw.environment;
import com.badlogic.gdx.graphics.*;
import com.kgc.sauw.utils.Langs;
import com.kgc.sauw.resource.Textures;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;

public class Items {
	public static class Type{
		public static final byte ITEM = 0;
		public static final byte BLOCKITEM = 1;
		public static final byte INSTRUMENT = 2;
		public static final byte FOOD = 3;
	}
	public static Type Type = new Type();
	Textures Textures;
	Langs l;
	public ArrayList<Item> ITEMS = new ArrayList<Item>();
	public Items(Textures t, Langs l) {
		this.l = l;
		this.Textures = t;
		createItem(0, 0f, null, null, -1, -1, -1);
		createItem(1, 0.01f, l.getString("grass"), Textures.grass0, Type.BLOCKITEM, 1, 64, 0);
		createItem(2, 0.2f, l.getString("apple"), Textures.apple, Type.FOOD, 64, 0);
		createItem(3, 0.01f, l.getString("stone"), Textures.stone, Type.BLOCKITEM, 2, 64, 0);
		createItem(4, 0.01f, l.getString("wall"), Textures.wall0, Type.BLOCKITEM, 3, 64, 0);
		createItem(5, 0.01f, l.getString("door"), Textures.door, Type.BLOCKITEM, 7,  64, 0);
		createItem(6, 0.01f, l.getString("chest"), Textures.chest, Type.BLOCKITEM, 5, 64, 0);
		createItem(7, 0.05f, l.getString("stick"), Textures.stick, Type.ITEM, 64, 0);
		createItem(8, 1.25f, l.getString("log"), Textures.log, Type.ITEM, 64, 0);
		createItem(10,0.01f, l.getString("wood"), Textures.wood, Type.BLOCKITEM, 8, 64, 0);
		createItem(11,0.01f, l.getString("stone"), Textures.stone_1, Type.BLOCKITEM, 9, 64, 0);
		createItem(12,0.15f, l.getString("stone"), Textures.stone_item, Type.ITEM, 64, 0);
		createItem(13,0.01f, l.getString("iron_ore"), Textures.iron_ore_item, Type.ITEM, 64, 0);
		createItem(14,0.75f, l.getString("stone_pickaxe"), Textures.stone_pickaxe, Type.INSTRUMENT, 1, 128);
		createItem(15,5.75f, l.getString("stone_axe"), Textures.stone_axe, Type.INSTRUMENT, 1, 10);
		createItem(16,0.01f, l.getString("furnace"), Textures.furnace_item, Type.BLOCKITEM, 11, 64, 0);
		createItem(17,0.1f,  l.getString("rope"), Textures.rope, Type.ITEM, 64, 0);
		createItem(18,0.05f, l.getString("vegetable_fiber"), Textures.vegetable_fiber, Type.ITEM, 64, 0);
		createItem(19,0.01f, l.getString("dirt"), Textures.dirt, Type.BLOCKITEM, 12, 64, 0);
		createItem(20,0.1f, l.getString("sapling"), Textures.sapling_item, Type.BLOCKITEM, 13, 64, 0);
		createItem(21,1f, l.getString("iron_ingot"),Textures.iron_ingot, Type.ITEM, 64, 0);
		createItem(22,0.75f, l.getString("stone_shovel"), Textures.stone_shovel, Type.INSTRUMENT, 1, 128);
		createItem(23,0.01f, l.getString("campfire"), Textures.campfire, Type.BLOCKITEM, 15, 64, 0);
		
		
		getItemById(2).setFoodScore(4);
		getItemById(14).setInstrumentType(1);
		getItemById(15).setInstrumentType(2);
		getItemById(22).setInstrumentType(4);
	}
	public void createItem(int id, float weight, String name, Texture t, int type, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, t, type, maxCount, maxData));
		getItemById(id).weight = weight;
	}
	public void createItem(int id, float weight, String name, Texture t, int type, int bi, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, t, type, bi, maxCount, maxData));
		getItemById(id).weight = weight;
	}
	public void createItem(int id, float weight, String StringId, String name, Texture t, int type, int bi, int maxCount, int maxData) {
		ITEMS.add(new Item(id, StringId, name, t, type, bi, maxCount, maxData));
		getItemById(id).weight = weight;
	}
	public void createItem(int id, float weight, String StringId, String name, Texture t, int type, int maxCount, int maxData) {
		ITEMS.add(new Item(id, StringId, name, t, type, maxCount, maxData));
		getItemById(id).weight = weight;
	}
	public void createItem(String StringId, float weight, String name, Texture t, int type, int bi, int maxCount, int maxData){
		createItem(getFirstFreeId(), weight, StringId, name, t, type, bi, maxCount, maxData);
	}
	public void createItem(String StringId, float weight, String name, Texture t, int type, int maxCount, int maxData) {
		createItem(getFirstFreeId(), weight, StringId, name, t, type, maxCount, maxData);
	}
	public int getFirstFreeId(){
		int ii = 1;
		for(Item i : ITEMS){
			if(ii != i.id) return ii;
			ii++;
		}
		return ITEMS.size() + 1;
	}
	public void createItems(FileHandle folder, FileHandle TexturesFolder){
		try {
			FileHandle[] craftFiles = folder.list();
			for (FileHandle file : craftFiles) {
				JSONObject item = new JSONObject(file.readString());
				
                String itemId = item.getString("id");
				String itemName = item.getString("itemName");
				Texture texture = new Texture(Gdx.files.external(TexturesFolder + "/" + item.getString("Texture")));
				int type = item.getInt("itemType");
				int maxCount = item.getInt("maxCount");
				int maxData = item.getInt("maxData");
				float weight = (float)item.getDouble("weight");
				createItem(itemId, weight, itemName, texture, type, maxCount, maxData);
			}
		} catch (Exception e) {
			Gdx.app.log("ModAPI_CreateItemError", e.toString());
		}
	}
	public Texture getTextureById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i).t;
			}
		}
		return Textures.undf;
	}
	public int getMaxCountById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i).maxCount;
			}
		}
		return -1;
	}
	public int getMaxDataById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i).maxData;
			}
		}
		return -1;
	}
	public Item getItemById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i);
			}
		}
		return null;
	}
	public String getNameById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i).name;
			}
		}
		return "Нет имени";
	}

    public static class Item{

        public int id;
        public String StringId;
        public Texture t;
        public String name;
        public int maxCount;
        public int maxData;
        public int type;
        public int blockId;
        public int intrumentType;
        private int foodScore;
        public float weight = 0.5f;

        public Item(int id, String name, Texture t, int type, int maxCount, int maxData ){
            this.id = id;
            this.t = t;
            this.name = name;
            this.type = type;
            this.maxCount = maxCount;
            this.maxData = maxData;
        }
        public Item(int id, String name, Texture t, int type, int bi, int maxCount, int maxData){
            this.id = id;
            this.t = t;
            this.name = name;
            this.type = type;
            this.blockId = bi;
            this.maxCount = maxCount;
            this.maxData = maxData;
        }
        public Item(int id, String StringId, String name, Texture t, int type, int bi, int maxCount, int maxData){
            this.id = id;
            this.StringId = StringId;
            this.t = t;
            this.name = name;
            this.type = type;
            this.blockId = bi;
            this.maxCount = maxCount;
            this.maxData = maxData;
        }
        public Item(int id, String StringId, String name, Texture t, int type, int maxCount, int maxData){
            this.id = id;
            this.StringId = StringId;
            this.t = t;
            this.name = name;
            this.type = type;
            this.maxCount = maxCount;
            this.maxData = maxData;
        }
        public void setInstrumentType(int type){
            this.intrumentType = type;
        }
        public void setFoodScore(int foodScore){
            this.foodScore = foodScore;
        }
        public int getFoodScore(){
            return foodScore;
        }
    }
}
