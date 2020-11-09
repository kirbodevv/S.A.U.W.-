package com.KGC.SAUW;
import com.badlogic.gdx.graphics.*;
import com.KGC.SAUW.Item;
import com.KGC.SAUW.Textures;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;

public class Items {
	Textures Textures;
	Langs l;
	public ArrayList<Item> ITEMS = new ArrayList<Item>();
	public Items(Textures t, Langs l) {
		this.l = l;
		this.Textures = t;
		createItem(1, l.getString("grass"), Textures.grass0, 1, 1, 64, 0);
		createItem(3, l.getString("stone"), Textures.stone, 1, 2, 64, 0);
		createItem(4, l.getString("wall"), Textures.wall0, 1, 3, 64, 0);
		createItem(5, l.getString("door"), Textures.door, 1, 7,  64, 0);
		createItem(6, l.getString("chest"), Textures.chest, 1, 5, 64, 0);
		createItem(7, l.getString("stick"), Textures.stick, 0, 64, 0);
		createItem(8, l.getString("log"), Textures.log, 0, 64, 0);
		createItem(10,l.getString("wood"), Textures.wood, 1, 8, 64, 0);
		createItem(11,l.getString("stone"), Textures.stone_1, 1, 9, 64, 0);
		createItem(12,l.getString("stone"), Textures.stone_item, 0, 64, 0);
		createItem(13,l.getString("iron_ore"), Textures.iron_ore_item, 0, 64, 0);
		createItem(14,l.getString("stone_pickaxe"), Textures.stone_pickaxe, 2, 1, 128);
		createItem(15,l.getString("stone_axe"), Textures.stone_axe, 2, 1, 10);
		createItem(16,l.getString("furnace"), Textures.furnace_item, 1, 11, 64, 0);
		createItem(17,l.getString("rope"), Textures.rope, 0, 64, 0);
		createItem(18,l.getString("vegetable_fiber"), Textures.vegetable_fiber, 0, 64, 0);
		createItem(19,l.getString("dirt"), Textures.dirt, 1, 12, 64, 0);
		createItem(20,l.getString("sapling"), Textures.sapling_item, 1, 13, 64, 0);
		createItem(21,l.getString("iron_ingot"),Textures.iron_ingot, 0, 64, 0);
		createItem(22,l.getString("stone_shovel"), Textures.undf,2, 1, 128);
		createItem(23,l.getString("campfire"), Textures.campfire, 1, 15, 64, 0);
		
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
	public void createItem(int id, String name, String t, int type, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, new Texture(Gdx.files.external("S.A.U.W./Mods/" + t)), type, maxCount, maxData));
	}
	public void createItem(int id, String name, String t, int type, int bi, int maxCount, int maxData) {
		ITEMS.add(new Item(id, name, new Texture(Gdx.files.external("S.A.U.W./Mods/" + t)) , type, bi, maxCount, maxData));
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
