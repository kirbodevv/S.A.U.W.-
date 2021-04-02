package com.kgc.sauw.entity;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;
import com.kgc.sauw.*;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.config.Settings;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.Time;
import com.kgc.sauw.map.Maps;
import com.kgc.sauw.map.World;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.math.Vector2d;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import java.util.ArrayList;
import java.util.Random;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;
import org.json.JSONObject;

public class Player implements ExtraData {
    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("health", health);
        buffer.put("hunger", hunger);
        buffer.put("coords", new int[]{(int) player.x, (int) player.y});
        buffer.put("InvLenght", Inventory.size());
        for (int i = 0; i < Inventory.size(); i++) {
            buffer.put("Inv_" + i, Inventory.get(i));
        }
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
        Inventory = new ArrayList<InventorySlot>(buffer.getInt("InvLenght"));
        for (int i = 0; i < buffer.getInt("InvLenght"); i++) {
            Inventory.add(i, new InventorySlot());
            Inventory.get(i).readBytes(buffer.getByteArray("Inv_" + i), begin, end);
        }
    }

    public JSONObject data;
    private int h = Gdx.graphics.getHeight();
    private int w = Gdx.graphics.getWidth();
    public int plW = w / 16 * 10 / 26;
    public int plH = w / 16;

    public int playerBodyW = plW;
    public int playerBodyH = plH / 4;

    public float posX = w / 2 + 16;
    public float posY = h / 2 - 32;
    public int carriedSlot = 0;
    private final com.kgc.sauw.environment.Items Items;
    private Textures t;
    public int[] hotbar = new int[8];
    public int currentTileX;
    public int currentTileY;

    public ArrayList<InventorySlot> Inventory = new ArrayList<InventorySlot>();

    public float maxWeight = 40.0f;
    public float weight = 0.0f;


    public int maxHealth = 20;
    public int health = 20;
    public int hunger = 10;

    private Animation walkL;
    private Animation walkR;
    private Animation walkU;
    private Animation walkD;

    public Body body;

    AchievementsChecker AC = new AchievementsChecker();
    private Maps maps;

    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    private GameInterface GI;
    public final float normalPlayerSpeed = w / 16;
    public float playerSpeed = 1.0f;
    public int rot = 0;
    private float stateTime;
    private Entities entities;
    public boolean isDead = true;
    private Settings settings;

    public Rectangle player;
    public Vector2d velocity = new Vector2d(0, 0);

    public int getCountOfItems(int id) {
        int count = 0;
        for (int i = 0; i < Inventory.size(); i++) {
            if (Inventory.get(i).id == id) {
                count += Inventory.get(i).count;
            }
        }
        return count;
    }

    public void deleteItems() {
        ArrayList<InventorySlot> toBeRemoved = new ArrayList<InventorySlot>(Inventory);
        Inventory.removeAll(toBeRemoved);
    }

    public void deleteItems(int id) {
        ArrayList<InventorySlot> toBeRemoved = new ArrayList<InventorySlot>();
        for (InventorySlot slot : Inventory) {
            if (slot.id == id)
                toBeRemoved.add(slot);
        }
        Inventory.removeAll(toBeRemoved);
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
            if (Inventory.get(i).id == id && Inventory.get(i).count < Items.getItemById(Inventory.get(i).id).maxCount)
                return i;
        }
        return -1;
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) kill();
    }

    public com.kgc.sauw.environment.Items.Item getCarriedItem() {
        return getItemFromHotbar(carriedSlot);
    }

    public com.kgc.sauw.environment.Items.Item getItemFromHotbar(int index) {
        if (hotbar[index] != -1 && Items.getItemById(Inventory.get(hotbar[index]).id) != null) {
            return Items.getItemById(Inventory.get(hotbar[index]).id);
        } else {
            return Items.getItemById(0);
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
            currentTileY = r.nextInt(maps.map0.length - 2) + 1;
            currentTileX = r.nextInt(maps.map0[0].length - 2) + 1;
            player.x = currentTileX * (w / 16);
            player.y = currentTileY * (w / 16);
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
        FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
        try {
            data.writeString(this.data.toString(), false);
        } catch (Exception e) {

        }
    }

    public Player(Items it, Textures t, GameInterface GI, Entities entities, Maps m, Settings settings) {
        this.Items = it;
        this.t = t;
        this.GI = GI;
        this.entities = entities;
        this.maps = m;
        this.settings = settings;
        this.player = new Rectangle();
        this.player.setSize(plW, plH);
        for (int i = 0; i < hotbar.length; i++) {
            this.hotbar[i] = -1;
        }
        try {
            String result = "";
            FileHandle data = Gdx.files.external("S.A.U.W./User/data.json");
            result = data.readString();
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

    public void render(SpriteBatch b, Textures t, Time WorldTime) {
        if (!isDead) {
            float AL = 0.75f - (Maths.module(720 - WorldTime.getTime()) / (720 / 0.3f));
            b.setColor(1f, 1f, 1f, AL);
            b.draw(t.shadow, body.getPosition().x - playerBodyW / 2, body.getPosition().y - playerBodyH / 2 - playerBodyH / 4, plW, plW);
            b.setColor(1f, 1f, 1f, 1f);
            b.draw(currentFrame, body.getPosition().x - playerBodyW / 2, body.getPosition().y - playerBodyH / 2, plW, plH);
        }
    }

    public void update(World world, Achievements a, Camera2D cam) {
        if (!isDead) {
            stateTime += Gdx.graphics.getDeltaTime();
            boolean isCameraZooming = false;
            for (int y = currentTileY - 3; y < currentTileY + 3; y++) {
                for (int x = currentTileX - 3; x < currentTileX + 3; x++) {
                    if (y > 0 && y < maps.map0.length && x > 0 && x < maps.map0[0].length) {
                        if (world.maps.map0[y][x][0].id == 15) {
                            cam.setCameraZoom(0.75f, 5);
                            isCameraZooming = true;
                        }
                    }
                }
            }
            if (!isCameraZooming) {
                cam.setCameraZoom(1.25f, 5);
            }

            weight = 0.0f;
            for (InventorySlot slot : Inventory) {
                weight += slot.count * Items.getItemById(slot.id).weight;
            }
            playerSpeed = 1.0f - ((weight * 1.66f) / 100);
            if (playerSpeed < 0) playerSpeed = 0;

            double velX = 0;
            double velY = 0;

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                velX = GI.j.normD().x;
                velY = GI.j.normD().y;
                if (GI.j.isTouched()) {
                    if (GI.j.angleI() < 315 && GI.j.angleI() > 225) {
                        rot = 0;
                    } else if (GI.j.angleI() < 225 && GI.j.angleI() > 135) {
                        rot = 1;
                    } else if (GI.j.angleI() > 45 && GI.j.angleI() < 135) {
                        rot = 2;
                    } else if (GI.j.angleI() < 45 || GI.j.angleI() > 315) {
                        rot = 3;
                    }
                }
            } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    velY = 1;
                    rot = 0;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    velY = -1;
                    rot = 2;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    velX = -1;
                    rot = 3;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    velX = 1;
                    rot = 1;
                }
            }
            if (rot == 0) {
                currentFrame = walkU.getKeyFrame(stateTime, true);
            } else if (rot == 1) {
                currentFrame = walkR.getKeyFrame(stateTime, true);
            } else if (rot == 2) {
                currentFrame = walkD.getKeyFrame(stateTime, true);
            } else if (rot == 3) {
                currentFrame = walkL.getKeyFrame(stateTime, true);
            }
            velocity.x = (velX * (playerSpeed));
            velocity.y = (velY * (playerSpeed));
            body.setLinearVelocity((float) velocity.x * normalPlayerSpeed * 2, (float) velocity.y * normalPlayerSpeed * 2);
            if (body.getPosition().x - playerBodyW / 2 == posX && body.getPosition().y - playerBodyH / 2 == posY) {
                if (rot == 1) {
                    currentFrame = walkFrames[3];
                } else if (rot == 3) {
                    currentFrame = walkFrames[1];
                } else if (rot == 0) {
                    currentFrame = walkFrames[9];
                } else if (rot == 2) {
                    currentFrame = walkFrames[8];
                }
            }
            posX = body.getPosition().x - playerBodyW / 2;
            posY = body.getPosition().y - playerBodyH / 2;
            currentTileX = (int) (((posX + plW / 2) - ((posX + plW / 2) % (w / 16))) / (w / 16));
            currentTileY = (int) (((posY + plH / 2) - ((posY + plH / 2) % (w / 16))) / (w / 16));

            player.setPosition(posX, posY);
            velocity.x = 0;
            velocity.y = 0;

            for (int i = 0; i < Inventory.size(); i++) {
                if (Inventory.get(i).data >= Items.getItemById(Inventory.get(i).id).maxData && Items.getItemById(Inventory.get(i).id).maxData != 0) {
                    Inventory.remove(i);
                }
            }
            if (settings.autopickup || (GI.interactionButton.isTouched() || Gdx.input.isKeyPressed(Input.Keys.E))) {
                for (int i = 0; i < entities.entities.size(); i++) {
                    if (entities.entities.get(i) instanceof ItemEntity && Maths.distanceD((int) posX, (int) posY, entities.entities.get(i).posX, entities.entities.get(i).posY) < w / 32) {
                        ItemEntity item = (ItemEntity) entities.entities.get(i);
                        addItem((int) item.getExtraData("itemId"), (int) item.getExtraData("itemCount"), (int) item.getExtraData("itemCount"));
                        entities.entities.remove(i);
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

    public static class InventorySlot implements ExtraData {
        @Override
        public byte[] getBytes() {
            DataBuffer buffer = new DataBuffer();
            buffer.put("id", id);
            buffer.put("count", count);
            buffer.put("data", data);
            return buffer.toBytes();
        }
        @Override
        public void readBytes(byte[] bytes, int begin, int end) {
            DataBuffer buffer = new DataBuffer();
            buffer.readBytes(bytes);
            id = buffer.getInt("id");
            count = buffer.getInt("count");
            data = buffer.getInt("data");
        }
        public int id, count, data;

        public void setItem(int id, int count, int data){
            this.id = id;
            this.count = count;
            this.data = data;
        }
        public void clear(){
            this.id = 0;
            this.count = 0;
            this.data = 0;
        }
    }
}