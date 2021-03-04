package com.kgc.sauw;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.InterfaceAPI.Interface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import com.kgc.sauw.InterfaceAPI.Slot;
import com.kgc.sauw.InterfaceAPI.Container;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Blocks {
	public ArrayList<Block> BLOCKS = new ArrayList<Block>();
	Items items;
	Textures Textures;
	int w = Gdx.graphics.getWidth();
	int h = Gdx.graphics.getHeight();
	TextureRegion[][] saplingTextures;
	TextureRegion[][] furnaceTextures;
	TextureRegion[][] campfireTextures;
	TextureRegion campfireTexture;
	
	Animation campfireAnimation;
	Animation furnaceAnimation;
	private Langs l;
	public Blocks(Textures t, Langs l) {
		this.l = l;
		this.Textures = t;
		createBlock(1, Textures.grass0);
		createBlock(2, Textures.stone);
		createBlock(3, Textures.wall0, t.wall1, t.wall2, t.wall3, t.wall4, t.wall5, t.wall6, t.wall7, t.wall8, t.wall9, t.wall10, t.wall0, t.wall1, t.wall0, t.wall1, t.wall0);
		createBlock(4, (Texture)null);
		createBlock(5, Textures.chest);
		createBlock(6, Textures.tree);
		createBlock(7, Textures.door);
		createBlock(8, Textures.wood);
		createBlock(9, Textures.stone_1);
        createBlock(10, Textures.iron_ore);
		createBlock(11, Textures.furnace);
		createBlock(12, Textures.dirt);
        createBlock(13, Textures.sapling);
		//препятствие
        createBlock(14, Textures.undf);
		createBlock(15, Textures.campfire);
		//куст
		createBlock(16, Textures.undf);
		createBlock(17, Textures.christmas_tree);
		createBlock(18, Textures.snow);
		
		getBlockById(6).setSize(1, 2);
		getBlockById(17).setSize(1, 2);
		getBlockById(13).setSize(1, 2);
		
		getBlockById(6).setMaxDamage(5);
		getBlockById(17).setMaxDamage(5);
		getBlockById(9).setMaxDamage(5);
		getBlockById(10).setMaxDamage(6);

		getBlockById(6).setTransparent();
		getBlockById(17).setTransparent();
		getBlockById(7).setTransparent();
		getBlockById(9).setTransparent();
		getBlockById(10).setTransparent();
		getBlockById(11).setTransparent();
        getBlockById(13).setTransparent();
        getBlockById(15).setTransparent();
		getBlockById(16).setTransparent();
		
		getBlockById(6).setDrop(new int[][]{{8, 3}, {20, 1}});
		getBlockById(17).setDrop(new int[][]{{8, 3}, {20, 1}});
		getBlockById(9).setDrop(new int[][]{{12, 5}});
		getBlockById(10).setDrop(new int[][]{{13, 4}});
		getBlockById(16).setDrop(new int[][]{{18, 1}});


		getBlockById(9).IT = 1;
		getBlockById(10).IT = 1;
		getBlockById(6).IT = 2;
		getBlockById(17).IT = 2;
		getBlockById(1).IT = 4;
		getBlockById(2).IT = 1;
		getBlockById(3).IT = 1;
		getBlockById(5).IT = 2;
		getBlockById(8).IT = 2;
		getBlockById(11).IT = 1;
		getBlockById(12).IT = 4;
		getBlockById(13).IT = 2;
        getBlockById(16).IT = 3;
		
		getBlockById(11).setLightingRadius(2);
		getBlockById(11).setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
		getBlockById(15).setLightingRadius(4);
		getBlockById(15).setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
		
		getBlockById(6).setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
		getBlockById(17).setCollisionsRectangleByPixels(11, 0, 10, 10, 32);
        getBlockById(11).setCollisionsRectangleByPixels(1, 0, 30, 13, 32);
	    getBlockById(13).setCollisionsRectangleByPixels(15, 0, 4, 4, 32);
		
	}
	public void setItems(Items items) {
		this.items = items;
		for (Block bl : BLOCKS) {
			bl.setStandartDrop(this);
		}
	}
	public Item getItemByBlockId(int id) {
		for (Item item : items.ITEMS) {
			if (item.type == 1 && item.blockId == id) {
				return item;
			}
		}
		return null;
	}
	public void createBlock(int id, Texture t0) {
		BLOCKS.add(new Block(id, t0));
	}
	public void createBlock(int id, Texture t0, Texture t1, Texture t2, Texture t3, Texture t4, Texture t5, Texture t6, Texture t7, Texture t8, Texture t9, Texture t10, Texture t11, Texture t12, Texture t13, Texture t14, Texture t15) {
	    BLOCKS.add(new Block(id, t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15));
	};
	public void createBlock(int id, String t0) {
		Texture tt0 = new Texture(Gdx.files.external("S.A.U.W./Mods/" + t0));
		this.createBlock(id, tt0);
		items.createItem(items.ITEMS.size(), 1f, "", tt0, 1, id, 64, 0);
	}
	public void initialize(final Textures t, Camera2D cam, final Items items, final GameInterface gi, final SpriteBatch  batch, final World world, final Langs langs) {
		final Maps maps = world.maps;
		getBlockById(5).registerTileEntity(new Block.TileEntity(){
			    @Override
				public void interfaceInitializate() {
					float x = h / (w / 16.0f) / 2 * (w / 16.0f);
					float y = w / 16.0f;
					float width = w / 16 * (h / (w / 16.0f) - 2);
					float heigth = w / 16 * (h / (w / 16.0f) - 2);
	               // String _interface = "{\"standart\":{\"header\":{\"text\":{\"text\":\"Chest\"}}, \"isBlockInterface\":true, \"inventory\" : {\"standart\":true}, \"background\" : {\"standart\" : true, \"full\" : false}}, \"elements\" : {";
					chestInterface = new Interface(Interface.InterfaceSizes.STANDART, Textures, batch, gi.interfaceCamera, items, gi);
					chestInterface.createInventory().setHeaderText(langs.getString("chest")).isBlockInterface(true);
					chestInterface.setMaps(maps);
				   for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 8; j++) {
							Slot slot = new Slot("chestSlot_" + (i * 8 + j), (int)(x + ((width - w / 24.0f * 8) / 2) + (w / 24 * j)), (int)(y + (w / 24 * 3 + w / 16 + w / 32) + w / 24 * i + w / 64), w / 24, w / 24, t.selected_slot);
							chestInterface.slots.add(slot);
							//_interface += "\"chestSlot_" + (i * 8 + j) + "\" : {\"type\":\"slot\", \"Texture_0\":\"Interface/slot.png\", \"Texture_1\":\"Interface/selected_slot.png\", \"width\":" + (w / 24) + ", \"height\":" + (w / 24) + ", \"x\":" + (int)(x + ((width - w / 24.0f * 8) / 2) + (w / 24 * j)) + ", \"y\":" + (int)(y + (w / 24 * 3 + w / 16 + w / 32) + w / 24 * i + w / 64) + "}" + ((i * 8 + j == 23) ? "" : ",");
						}
					}
					//_interface += "}, \"drawing\" : []}";
				}
				@Override
				public void randomTick(Tile tile) {

				}
			    Interface chestInterface;
				@Override
				public void initialize(Tile tile) {

				}

				@Override
				public void tick(Tile tile) {
				}

				@Override
				public void click(Tile tile) {
				}

				@Override
				public Interface getGuiScreen() {
					return chestInterface;
				}

				@Override
				public void onInteractionButtonPressed(Tile tile) {
				}
			});
		getBlockById(7).registerTileEntity(new Block.TileEntity(){

			    @Override
				public boolean collisionsIf(Tile tile) {
					if (tile.getExtraData("isOpen") != null)
					    return !(boolean)tile.getExtraData("isOpen");
					return true;
				}

				@Override
				public boolean renderIf(Tile tile) {
					if (tile.getExtraData("isOpen") != null)
					    return !(boolean)tile.getExtraData("isOpen");
					return true;
				}


				@Override
				public void initialize(Tile tile) {
					tile.setExtraData("isOpen", false);
				}

				@Override
				public void tick(Tile tile) {
				}

				@Override
				public void click(Tile tile) {
				}

				@Override
				public Interface getGuiScreen() {
					return null;
				}

				@Override
				public void onInteractionButtonPressed(Tile tile) {
					tile.setExtraData("isOpen", !(boolean)tile.getExtraData("isOpen"));
				}

				@Override
				public void randomTick(Tile tile) {

				}
			});
		campfireTexture = TextureRegion.split(getBlockById(15).t0, getBlockById(15).t0.getWidth(), getBlockById(15).t0.getHeight())[0][0];
		campfireTextures = TextureRegion.split(Textures.campfire_animation, Textures.campfire_animation.getWidth() / 4, Textures.campfire_animation.getHeight());
		campfireAnimation = new Animation(0.05f, campfireTextures[0][0], campfireTextures[0][1], campfireTextures[0][2], campfireTextures[0][3]);
		getBlockById(15).registerTileEntity(new Block.TileEntity(){
                float stateTime = 0.0f;
				@Override
				public void initialize(Tile tile) {
				}
				@Override
				public void tick(Tile tile) {
					stateTime += Gdx.graphics.getDeltaTime();
					tile.t = campfireAnimation.getKeyFrame(stateTime, true);
				}
				@Override
				public void click(Tile tile) {
				}
				@Override
				public Interface getGuiScreen() {
					return null;
				}
				@Override
				public void onInteractionButtonPressed(Tile tile) {
				}
				@Override
				public void randomTick(Tile tile) {
				}
		});
	    furnaceTextures = TextureRegion.split(getBlockById(11).t0, getBlockById(11).t0.getWidth() / 4, getBlockById(11).t0.getHeight());
		furnaceAnimation = new Animation(0.2f, furnaceTextures[0][1], furnaceTextures[0][2], furnaceTextures[0][2]);
		getBlockById(11).registerTileEntity(new Block.TileEntity(){
				float stateTime = 0.0f;
				Interface _interface;
				Slot resultSlot;
				Slot fuelSlot;
				Slot ingSlot;
				int[][] recipes = new int[][]{
					{13, 21}
				};
				int[][] fuel = new int[][]{
					{8, 5}
				};
				@Override
				public void interfaceInitializate() {
					_interface = new Interface(Interface.InterfaceSizes.STANDART, Textures, batch, gi.interfaceCamera, items, gi);
					_interface.setHeaderText(langs.getString("furnace")).isBlockInterface(true).createInventory();
					_interface.setMaps(maps);
					int temp = (int)(_interface.width - w / 24 * 4) / 2;
					resultSlot = new Slot("ResultSlot", (int)(_interface.x + _interface.width - temp - w / 24), (int)(_interface.y + w / 24 * 6.5), w / 24, w / 24, Textures.selected_slot);
					ingSlot = new Slot("IngSlot", (int)(_interface.x + temp), (int)(_interface.y + w / 24 * 7.5), w / 24, w / 24, Textures.selected_slot);
					fuelSlot = new Slot("FuelSlot", (int)(_interface.x + temp), (int)(_interface.y + w / 24 * 5.5), w / 24, w / 24, Textures.selected_slot);
					resultSlot.setSF(new Slot.SlotFunctions(){
							@Override
							public boolean isValid(int id, int count, int data, String FromSlotWithId) {
								return false;
							}
							@Override
							public void onClick(){
								
							}
						});
					this._interface.slots.add(resultSlot);
					this._interface.slots.add(fuelSlot);
					this._interface.slots.add(ingSlot);
				}
				@Override
				public void initialize(Tile tile) {
					tile.setExtraData("progress", 0);
					tile.setExtraData("fuel", 0);
					tile.setExtraData("curRecId", 0);
					tile.t = furnaceTextures[0][0];
				}

				@Override
				public void tick(Tile tile) {
					stateTime += Gdx.graphics.getDeltaTime();
					tile.t = furnaceTextures[0][0];
					if ((int)(tile.getExtraData("fuel")) <= 0) {
						for (int i = 0; i < fuel.length; i++) {
							Container fuelCon = tile.getContainer("FuelSlot");
							for (int j = 0; j < recipes.length; j++) {
								if (fuelCon.getId() == fuel[i][0] && tile.getContainer("IngSlot").getId() == recipes[i][0]) {
									fuelCon.setItem(fuelCon.getId(), fuelCon.getCount() - 1, fuelCon.getData());
									tile.setExtraData("fuel", 20 * fuel[i][1]);
								}
							}
						}
					} else {
						tile.setExtraData("fuel", (int)tile.getExtraData("fuel") - 1);
						tile.t = furnaceAnimation.getKeyFrame(stateTime, true);
						if ((int)(tile.getExtraData("progress")) <= 0) {
							for (int i = 0; i < recipes.length; i++) {
								Container ingCon = tile.getContainer("IngSlot");
								if (ingCon.getId() == recipes[i][0] && (tile.getContainer("ResultSlot").getId() == recipes[i][1] || tile.getContainer("ResultSlot").getId() == 0) && tile.getContainer("ResultSlot").getCount() < items.getItemById(recipes[i][1]).maxCount) {
									tile.setExtraData("progress", 100);
									tile.setExtraData("curRecId", recipes[i][1]);
								}
							}
						} else {
							tile.setExtraData("progress", (int)tile.getExtraData("progress") - 1);
							if ((int)tile.getExtraData("progress") <= 0) {
								Container res = tile.getContainer("ResultSlot");
								Container ing = tile.getContainer("IngSlot");
								ing.setItem(ing.getId(), ing.getCount() - 1, ing.getData());
								res.setItem((int)tile.getExtraData("curRecId"), res.getCount() + 1, res.getData());
							}
						}
					}
				}

				@Override
				public void click(Tile tile) {
				}

				@Override
				public Interface getGuiScreen() {
					return _interface;
				}

				@Override
				public void onInteractionButtonPressed(Tile tile) {
				}

				@Override
				public void randomTick(Tile tile) {
				}
			});
		saplingTextures = TextureRegion.split(getBlockById(13).t0, getBlockById(13).t0.getWidth() / 4, getBlockById(13).t0.getHeight());
		getBlockById(13).registerTileEntity(new Block.TileEntity(){
				@Override
				public void initialize(Tile tile) {
					tile.setExtraData("age", 0);
					tile.t = saplingTextures[0][0];
				}

				@Override
				public void tick(Tile tile) {
					if ((int)tile.getExtraData("age") < 1) {
						tile.t = saplingTextures[0][0];
					} else if ((int)tile.getExtraData("age") < 2) {
						tile.t = saplingTextures[0][1];
					} else if ((int)tile.getExtraData("age") < 3) {
						tile.t = saplingTextures[0][2];
					} else if ((int)tile.getExtraData("age") < 4) {
						tile.t = saplingTextures[0][3];
					}
				}

				@Override
				public void click(Tile tile) {
				}

				@Override
				public Interface getGuiScreen() {
					return null;
				}

				@Override
				public void onInteractionButtonPressed(Tile tile) {
				}

				@Override
				public void randomTick(Tile tile) {
					tile.setExtraData("age", (int)tile.getExtraData("age") + 1);
					if ((int)tile.getExtraData("age") >= 4) {
						world.setBlock(tile.x, tile.y, tile.z, getBlockById(6));
					}
				}
			});
	}
	public Block getBlockById(int id) {
		for (int i = 0; i < BLOCKS.size(); i++) {
			if (BLOCKS.get(i).id == id) return BLOCKS.get(i);
		}
		return null;
	}
    public void interfacesUpdate(Maps maps, Player pl, Camera2D cam) {
		for (int y = 0; y < maps.map0.length; y++) {
			for (int x = 0; x < maps.map0[y].length; x++) {
				for (int z = 0; z < maps.map0[y][x].length; z++) {
					if (maps.map0[y][x][z].TileEntity != null && maps.map0[y][x][z].Interface != null) {
						maps.map0[y][x][z].Interface.update(pl, cam);
					}
				}
			}
		}
	}
	public void interfacesRender(Maps maps, Player pl, Camera2D cam) {
		for (int i = 0; i < maps.map0.length; i++) {
			for (int j = 0; j < maps.map0[i].length; j++) {
				for (int l=0; l < maps.map0[i][j].length; l++) {
					if (maps.map0[i][j][l].TileEntity != null && maps.map0[i][j][l].Interface != null) {
						maps.map0[i][j][l].Interface.render(pl, cam);
					}
				}
			}
		}
	}
}

