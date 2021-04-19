package com.kgc.sauw.environment;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.environment.items.*;

import java.util.ArrayList;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Items {
    public static class Type {
        public static final byte ITEM = 0;
        public static final byte BLOCKITEM = 1;
        public static final byte INSTRUMENT = 2;
        public static final byte FOOD = 3;
    }

    public ArrayList<Item> ITEMS = new ArrayList<Item>();

    public Items() {
        addItem(new NullItem());
        addItem(new Grass());
        addItem(new Apple());
        addItem(new Chest());
        addItem(new Stick());
        addItem(new Log());
        addItem(new Stone());
        addItem(new IronOre());
        addItem(new StonePickaxe());
        addItem(new StoneAxe());
        addItem(new StoneShovel());
        addItem(new Furnace());
        addItem(new Rope());
        addItem(new VegetableFiber());
        addItem(new Sapling());
        addItem(new IronIngot());
        addItem(new Campfire());
    }

    /* public void createItem(int id, float weight, String name, Texture t, int type, int maxCount, int maxData) {
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

     public void createItem(String StringId, float weight, String name, Texture t, int type, int bi, int maxCount, int maxData) {
         createItem(getFirstFreeId(), weight, StringId, name, t, type, bi, maxCount, maxData);
     }

     public void createItem(String StringId, float weight, String name, Texture t, int type, int maxCount, int maxData) {
         createItem(getFirstFreeId(), weight, StringId, name, t, type, maxCount, maxData);
     }*/
    public void addItem(Item item) {
        ITEMS.add(item);
    }

    public int getFirstFreeId() {
        int ii = 1;
        for (Item i : ITEMS) {
            if (ii != i.id) return ii;
            ii++;
        }
        return ITEMS.size() + 1;
    }

    /*public void createItems(FileHandle folder, FileHandle TexturesFolder) {
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
                float weight = (float) item.getDouble("weight");
                createItem(itemId, weight, itemName, texture, type, maxCount, maxData);
            }
        } catch (Exception e) {
            Gdx.app.log("ModAPI_CreateItemError", e.toString());
        }
    }*/

    public Texture getTextureById(int id) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).id == id) {
                return ITEMS.get(i).t;
            }
        }
        return TEXTURES.undf;
    }

    public int getMaxCountById(int id) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).id == id) {
                return ITEMS.get(i).getItemConfiguration().maxCount;
            }
        }
        return -1;
    }

    public int getMaxDataById(int id) {
        for (int i = 0; i < ITEMS.size(); i++) {
            if (ITEMS.get(i).id == id) {
                return ITEMS.get(i).getItemConfiguration().maxData;
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
                return ITEMS.get(i).getItemConfiguration().name;
            }
        }
        return "Нет имени";
    }

}
