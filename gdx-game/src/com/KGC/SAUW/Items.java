package com.KGC.SAUW;
import com.badlogic.gdx.graphics.*;
import com.KGC.SAUW.Item;
import com.KGC.SAUW.Textures;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.json.JSONObject;
import org.json.JSONArray;

public class Items {
	public static class Type{
		public static int ITEM = 0;
		public static int BLOCKITEM = 1;
		public static int INSTRUMENT = 2;
		public static int FOOD = 3;
	}
	Textures Textures;
	Langs l;
	public ArrayList<Item> ITEMS = new ArrayList<Item>();
	public Items(Textures t, Langs l) {
		this.l = l;
		this.Textures = t;
		createItem(1, l.getString("grass"), Textures.grass0, Type.BLOCKITEM, 1, 64, 0);
		createItem(2, l.getString("apple"), Textures.apple, Type.FOOD, 64, 0);
		createItem(3, l.getString("stone"), Textures.stone, Type.BLOCKITEM, 2, 64, 0);
		createItem(4, l.getString("wall"), Textures.wall0, Type.BLOCKITEM, 3, 64, 0);
		createItem(5, l.getString("door"), Textures.door, Type.BLOCKITEM, 7,  64, 0);
		createItem(6, l.getString("chest"), Textures.chest, Type.BLOCKITEM, 5, 64, 0);
		createItem(7, l.getString("stick"), Textures.stick, Type.ITEM, 64, 0);
		createItem(8, l.getString("log"), Textures.log, Type.ITEM, 64, 0);
		createItem(10,l.getString("wood"), Textures.wood, Type.BLOCKITEM, 8, 64, 0);
		createItem(11,l.getString("stone"), Textures.stone_1, Type.BLOCKITEM, 9, 64, 0);
		createItem(12,l.getString("stone"), Textures.stone_item, Type.ITEM, 64, 0);
		createItem(13,l.getString("iron_ore"), Textures.iron_ore_item, Type.ITEM, 64, 0);
		createItem(14,l.getString("stone_pickaxe"), Textures.stone_pickaxe, Type.INSTRUMENT, 1, 128);
		createItem(15,l.getString("stone_axe"), Textures.stone_axe, Type.INSTRUMENT, 1, 10);
		createItem(16,l.getString("furnace"), Textures.furnace_item, Type.BLOCKITEM, 11, 64, 0);
		createItem(17,l.getString("rope"), Textures.rope, Type.ITEM, 64, 0);
		createItem(18,l.getString("vegetable_fiber"), Textures.vegetable_fiber, Type.ITEM, 64, 0);
		createItem(19,l.getString("dirt"), Textures.dirt, Type.BLOCKITEM, 12, 64, 0);
		createItem(20,l.getString("sapling"), Textures.sapling_item, Type.BLOCKITEM, 13, 64, 0);
		createItem(21,l.getString("iron_ingot"),Textures.iron_ingot, Type.ITEM, 64, 0);
		createItem(22,l.getString("stone_shovel"), Textures.undf, Type.INSTRUMENT, 1, 128);
		createItem(23,l.getString("campfire"), Textures.campfire, Type.BLOCKITEM, 15, 64, 0);
		
		getItemById(14).setInstrumentType(1);
		getItemById(15).setInstrumentType(2);
		getItemById(22).setInstrumentType(4);
	}
	public void createItem(int id, String name, Texture t, int type, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, t, type, maxCount, maxData));
	}
	public void createItem(int id, String name, Texture t, int type, int bi, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, t, type, bi, maxCount, maxData));
	}
	public void createItem(int id, String StringId, String name, Texture t, int type, int bi, int maxCount, int maxData) {
		ITEMS.add(new Item(id, StringId, name, t, type, bi, maxCount, maxData));
	}
	public void createItem(int id, String StringId, String name, Texture t, int type, int maxCount, int maxData) {
		ITEMS.add(new Item(id, StringId, name, t, type, maxCount, maxData));
	}
	public void createItem(String StringId, String name, Texture t, int type, int bi, int maxCount, int maxData){
		createItem(getFirstFreeId(), StringId, name, t, type, bi, maxCount, maxData);
	}
	public void createItem(String StringId, String name, Texture t, int type, int maxCount, int maxData) {
		createItem(getFirstFreeId(), StringId, name, t, type, maxCount, maxData);
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
				createItem(itemId, itemName, texture, type, maxCount, maxData);
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
	/*public int getBlockTypeById(int id){
	 for(int i = 0; i < ITEMS.size(); i++){
	 if(ITEMS.get(i).id == id && ITEMS.get(i).type == 1){
	 return ITEMS.get(i).blockType;
	 }
	 }
	 return -1;
	 }*/
	public int getTypeById(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				return ITEMS.get(i).type;
			}
		}
		return -1;
	}
	public int getBlockId(int id) {
		for (int i = 0; i < ITEMS.size(); i++) {
			if (ITEMS.get(i).id == id) {
				if (ITEMS.get(i).type == 1 || ITEMS.get(i).type == 2) {
					return ITEMS.get(i).blockId;
				} else {
					return -1;
				}
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
}
