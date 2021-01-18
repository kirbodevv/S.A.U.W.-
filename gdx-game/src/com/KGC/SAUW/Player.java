package com.KGC.SAUW;
import android.os.Environment;
import com.KGC.SAUW.InventorySlot;
import com.KGC.SAUW.Maps;
import com.KGC.SAUW.Textures;
import com.KGC.SAUW.mobs.ItemMob;
import com.KGC.SAUW.mobs.Mobs;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONObject;

public class Player implements ExtraData {
	@Override
	public byte[] getBytes() {
		DataBuffer buffer = new DataBuffer();
		buffer.put("health", health);
		buffer.put("hunger", hunger);
		buffer.put("coords", new int[]{(int)player.x, (int)player.y});
		/*for(int i = 0; i < Inventory.size(); i++){
		 buffer.put("Inv_" + i, Inventory.get(i));
		 }*/
		return buffer.toBytes();
	}
	@Override
	public void readBytes(byte[] bytes, int begin, int end) {
		DataBuffer buffer = new DataBuffer();
		buffer.readBytes(bytes);
		player.x = buffer.getIntArray("coords")[0];
		player.y = buffer.getIntArray("coords")[1];
		health = buffer.getInt("health");
		hunger = buffer.getInt("hunger");
		/*for(int i = 0; i < Inventory.; i++){
		 Inventory[i].readBytes(buffer.getByteArray("Inv_" + i), begin, end);
		 }*/
	}
	JSONObject data;
	int h = Gdx.graphics.getHeight();
	int w = Gdx.graphics.getWidth();
	public int plW = w / 16 * 10 / 26;
	public int plH = w / 16;
	public int posX = w / 2 + 16;
	public int posY = h / 2 - 32;
	int carriedSlot = 0;
	Items Items;
	//maps m;
	Textures t;
	Collisions CWB = new Collisions();
	public int[] hotbar = new int[8];
	public ArrayList<InventorySlot> Inventory = new ArrayList<InventorySlot>();
	public int mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
	public int mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));

	public float maxWeight = 40.0f;
	public float weight = 0.0f;


	public int maxHealth = 20;
	public int health = 20;
	public int hunger = 10;

	Animation walkL;
	Animation walkR;
	Animation walkU;
	Animation walkD;


	AchievementsChecker AC = new AchievementsChecker();
	private Maps maps;

	//public int acX,acY;
	TextureRegion[] walkFrames;
	TextureRegion currentFrame;
	GameInterface GI;
	int playerSpeed = w / 96;
	int rot = 0;
	float stateTime;
	Mobs mobs;
	public boolean isDead = true;
	private Settings settings;

	Rectangle player;
	Rectangle posWithAcX = new Rectangle();
	Rectangle posWithAcY = new Rectangle();
    Vector2i velocity = new Vector2i(0, 0);
	/*public int getFirstFreeSlot() {
	 for (int i = 0; i < Inventory.s; i++) {
	 if (Inventory[i].id == 0) return i;
	 }
	 return -1;
	 }*/
	public int getCountOfItems(int id) {
		int count = 0;
		for (int i = 0; i < Inventory.size(); i++) {
			if (Inventory.get(i).id == id) {
				count += Inventory.get(i).count;
			}
		}
		return count;
	}
	public void deleteItems(int id, int count) {
		int r = count;
		for (int i = 0; i < Inventory.size(); i++) {
			if (Inventory.get(i).id == id) {
				if (Inventory.get(i).count > r) {
					Inventory.get(i).count = Inventory.get(i).count - r;
					r -= r;
				} else {
					r -= Inventory.get(i).count;
					clearSlot(i);
				}
			}
		}
	}
	public int getFirstSlotWithId(int id) {
		for (int i = 0; i < Inventory.size(); i++) {
			if (Inventory.get(i).id == id && Inventory.get(i).count < Items.getItemById(Inventory.get(i).id).maxCount) return i;
		}
		return -1;
	}
	/*public boolean isSlotFree(int slot) {
	 if (Inventory[slot].id == 0) {
	 return true;
	 } else {
	 return false;
	 }
	 }*/
	public void hit(int damage) {
		health -= damage;
		if (health <= 0) kill();
	}
	public Item getCarriedItem() {
		return getItemFromHotbar(carriedSlot);
	}
	public Item getItemFromHotbar(int index){
		if (hotbar[index] != -1 && Items.getItemById(Inventory.get(hotbar[index]).id) != null) {
			return Items.getItemById(Inventory.get(hotbar[index]).id);
		} else {
			return Items.getItemById(-1);
		}
	}
	public ArrayList<InventorySlot> getInventory() {
		return Inventory;
	}
	public void kill() {
		isDead = true;
		GI.deadInterface.open();
	}
	public void spawn() {
		if (isDead) {
			Random r = new Random();
			mY = r.nextInt(maps.map0.length - 2) + 1;
			mX = r.nextInt(maps.map0[0].length - 2) + 1;
			player.x = mX * (w / 16);
			player.y = mY * (w / 16);
		}
		isDead = false;
		health = maxHealth;
	}
	public boolean addItem(int id, int count, int data) {
		for (int i = 0; i < Inventory.size(); i++) {
			if (Inventory.get(i).id == id && Inventory.get(i).count < Items.getItemById(Inventory.get(i).id).maxCount) {
			    int canadd = Items.getItemById(Inventory.get(i).id).maxCount - Inventory.get(i).count;
				if (canadd > count) {
					Inventory.get(i).count = Inventory.get(i).count + count;
					count -= count;
				} else {
					Inventory.get(i).count = Inventory.get(i).count + canadd;
					count -= canadd;
				}
			}
			if (count == 0) {
				return true;
			}
		}
		if (count > 0) {
			int slotsCount = (count % Items.getItemById(id).maxCount) + 1;
			for (int i = 0; i < slotsCount; i++)
				Inventory.add(new InventorySlot());
		}
		for (int i = 0; i < Inventory.size(); i++) {
			if (Inventory.get(i).id == 0) {
				int canadd = Items.getItemById(id).maxCount;
				Inventory.get(i).id = id;
				if (canadd > count) {
					Inventory.get(i).count = Inventory.get(i).count + count;
					count -= count;
				} else {
					Inventory.get(i).count = Inventory.get(i).count + canadd;
					count -= canadd;
				}
			}
			if (count == 0) {
				return true;
			}
		}
		return false;
	}
	public void clearSlot(int slot) {
		Inventory.remove(slot);
	}
	public void saveData() {
		File data = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./User/data.json");
		try {
			FileWriter s = new FileWriter(data.toString());
			s.write(this.data.toString());
			s.close();
		} catch (Exception e) {

		}
	}
	public Player(Items it, Textures t, GameInterface GI, Mobs mobs, Maps m, Settings settings) {
		this.Items = it;
		this.t = t;
		this.GI = GI;
		this.mobs = mobs;
		this.maps = m;
		this.settings = settings;
	    this.player = new Rectangle();
		this.player.setSize(plW, plH);
		posWithAcX.setSize(plW, plH);
		posWithAcY.setSize(plW, plH);
		for (int i = 0; i < hotbar.length; i++) {
			this.hotbar[i] = -1;
		}
		try {
			String result = "";
			File data = new File(Environment.getExternalStorageDirectory().toString() + "/S.A.U.W./User/data.json");
			FileReader s = new FileReader(data.toString());
			Scanner scanner = new Scanner(s);
			while (scanner.hasNextLine()) {
				result += scanner.nextLine();
			}
			s.close();
			this.data = new JSONObject(result);
		} catch (Exception e) {
			Gdx.app.log("error", e.toString());
		}

		TextureRegion[][] tmp = TextureRegion.split(t.player, t.player.getWidth() / 4, t.player.getHeight() / 3);
		walkFrames = new TextureRegion[4 * 3];
		int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
		walkL = new Animation(0.2f, walkFrames[0], walkFrames[1]);
		walkR = new Animation(0.2f, walkFrames[2], walkFrames[3]);
		walkD = new Animation(0.2f, walkFrames[4], walkFrames[5]);
		walkU = new Animation(0.2f, walkFrames[6], walkFrames[7]);
		currentFrame = walkFrames[4];
		stateTime = 0f;
		spawn();
	}
	public void render(SpriteBatch b, Textures t) {
		if (!isDead) {
			b.draw(currentFrame, this.posX, this.posY, plW, plH);
		}
	}
	public void update(World world, Achievements a) {
		if (!isDead) {
			stateTime += Gdx.graphics.getDeltaTime();
			if (GI.j.isTouched()) {
				if (GI.j.angleI() < 315 && GI.j.angleI() > 225) {
					rot = 0;
					currentFrame = walkU.getKeyFrame(stateTime, true);
				} else if (GI.j.angleI() < 225 && GI.j.angleI() > 135) {
					rot = 1;
					currentFrame = walkR.getKeyFrame(stateTime, true);
				} else if (GI.j.angleI() > 45 && GI.j.angleI() < 135) {
					rot = 2;
					currentFrame = walkD.getKeyFrame(stateTime, true);
				} else if (GI.j.angleI() < 45 || GI.j.angleI() > 315) {
					rot = 3;
					currentFrame = walkL.getKeyFrame(stateTime, true);
				}
			} else {
				if (rot == 0) {
					currentFrame = walkFrames[9];
				} else if (rot == 1) {
					currentFrame = walkFrames[3];
				} else if (rot == 2) {
					currentFrame = walkFrames[8];
				} else if (rot == 3) {
					currentFrame = walkFrames[1];
				}
			}
			weight = 0.0f;
			for (InventorySlot slot : Inventory) {
				weight += slot.count * Items.getItemById(slot.id).weight;
			}
			mX = (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
			mY = (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));
			velocity.x = (int)(GI.j.normD().x * playerSpeed);
			velocity.y = (int)(GI.j.normD().y * playerSpeed);
			posWithAcX.setPosition((float)(player.x + velocity.x), player.y);
			posWithAcY.setPosition(player.x, (float)(player.y + velocity.y));
			CWB.getCollisionWithBlocks(player, posWithAcX, posWithAcY, velocity, maps);
			player.x += velocity.x;
			player.y += velocity.y;
			posX = (int)player.x;
			posY = (int)player.y;
			player.setPosition(posX, posY);
		    velocity.x = 0;
			velocity.y = 0;

			for (int i = 0; i < Inventory.size(); i++) {
				if (Inventory.get(i).data >= Items.getItemById(Inventory.get(i).id).maxData && Items.getItemById(Inventory.get(i).id).maxData != 0) {
				    Inventory.remove(i);
				}
			}
			if ((!settings.autopickup && GI.interactionButton.isTouched()) || settings.autopickup) {
				for (int i = 0; i < mobs.mobs.size(); i++) {
					if (mobs.mobs.get(i) instanceof ItemMob && Maths.distanceD(posX, posY, mobs.mobs.get(i).posX, mobs.mobs.get(i).posY) < w / 32) {
						ItemMob item = (ItemMob)mobs.mobs.get(i);
						addItem(item.getExtraData("itemId"), item.getExtraData("itemCount"), item.getExtraData("itemCount"));
						mobs.mobs.remove(i);
					}
				}
			}
			/*if (GI.interactionButton.wasClicked) {
			 if (getCarriedItem().type == Items.Type.FOOD) {
			 hunger += getCarriedItem().getFoodScore();
			 if (hunger > 20) hunger = 20;
			 Inventory[carriedSlot].count -= 1;
			 }
			 }*/
			/*if (GI.dropButton.isTouched()) {
			 if (!isSlotFree(carriedSlot)) {
			 int x = posX;
			 int y = posY;
			 if (rot == 0) y += w / 16;
			 if (rot == 1) x += w / 16;
			 if (rot == 2) y -= w / 16;
			 if (rot == 3) x -= w / 16;
			 mobs.spawn(new ItemMob(x, y, Inventory[carriedSlot].id, Inventory[carriedSlot].count, Inventory[carriedSlot].data, Items));
			 clearSlot(carriedSlot);
			 }
			 }*/
		}
		AC.update(world, this, a, GI, settings);
	}
}
