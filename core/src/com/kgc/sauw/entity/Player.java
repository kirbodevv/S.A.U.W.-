package com.kgc.sauw.entity;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;
import com.kgc.sauw.*;
import com.kgc.sauw.environment.Time;
import com.kgc.sauw.environment.items.Item;
import com.kgc.sauw.map.World;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.math.Vector2d;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;

import java.util.ArrayList;
import java.util.Random;

import com.kgc.sauw.utils.Camera2D;
import org.json.JSONObject;

import static com.kgc.sauw.UI.Interfaces.Interfaces.DEAD_INTERFACE;
import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.ENTITIES;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.MAPS;


public class Player implements ExtraData {
    public double velX;
    public double velY;

    static {
        SPEED_RATIO_X = Gdx.graphics.getWidth() / 1280.0f;
        SPEED_RATIO_Y = Gdx.graphics.getHeight() / 720.0f;
    }

    public static float SPEED_RATIO_X;
    public static float SPEED_RATIO_Y;

    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("health", health);
        buffer.put("hunger", hunger);
        buffer.put("coords", new int[]{(int) playerBody.x, (int) playerBody.y});
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
        playerBody.x = buffer.getIntArray("coords")[0];
        playerBody.y = buffer.getIntArray("coords")[1];
        health = buffer.getInt("health");
        hunger = buffer.getInt("hunger");
        Inventory = new ArrayList<InventorySlot>(buffer.getInt("InvLenght"));
        for (int i = 0; i < buffer.getInt("InvLenght"); i++) {
            Inventory.add(i, new InventorySlot());
            Inventory.get(i).readBytes(buffer.getByteArray("Inv_" + i), begin, end);
        }
    }

    public JSONObject data;
    public int plW = BLOCK_SIZE * 10 / 26;
    public int plH = BLOCK_SIZE;

    public int playerBodyW = plW;
    public int playerBodyH = plH / 4;

    public float posX = SCREEN_WIDTH / 2 + 16;
    public float posY = SCREEN_HEIGHT / 2 - 32;
    public int carriedSlot = 0;
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

    AchievementsChecker achievementsChecker = new AchievementsChecker();

    private TextureRegion[] walkFrames;
    private TextureRegion currentFrame;
    public final float normalPlayerSpeed = SCREEN_WIDTH / 16;
    public float playerSpeed = 1.0f;
    public int rot = 0;
    private float stateTime;
    public boolean isDead = true;

    public Rectangle playerBody;
    public Vector2d velocity = new Vector2d(0, 0);

    private ArrayList<InventorySlot> slotsToRemove = new ArrayList<InventorySlot>();


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
        ArrayList<InventorySlot> toBeRemoved = new ArrayList<>(Inventory);
        Inventory.removeAll(toBeRemoved);
    }

    public void deleteItems(int id) {
        ArrayList<InventorySlot> toBeRemoved = new ArrayList<>();
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
            if (Inventory.get(i).id == id && Inventory.get(i).count < ITEMS.getItemById(Inventory.get(i).id).getItemConfiguration().maxCount)
                return i;
        }
        return -1;
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) kill();
    }

    public Item getCarriedItem() {
        return getItemFromHotbar(carriedSlot);
    }

    public Item getItemFromHotbar(int index) {
        if (hotbar[index] != -1 && hotbar[index] < Inventory.size() && ITEMS.getItemById(Inventory.get(hotbar[index]).id) != null) {
            return ITEMS.getItemById(Inventory.get(hotbar[index]).id);
        } else {
            hotbar[index] = -1;
            return ITEMS.getItemById(0);
        }
    }

    public ArrayList<InventorySlot> getInventory() {
        return Inventory;
    }

    public void kill() {
        isDead = true;
        DEAD_INTERFACE.open();
    }

    public void spawn() {
        if (isDead) {
            Random r = new Random();
            currentTileY = r.nextInt(MAPS.map0.length - 2) + 1;
            currentTileX = r.nextInt(MAPS.map0[0].length - 2) + 1;
            playerBody.x = currentTileX * (SCREEN_WIDTH / 16);
            playerBody.y = currentTileY * (SCREEN_WIDTH / 16);
        }
        isDead = false;
        health = maxHealth;
    }

    public boolean addItem(int id, int count, int data) {
        for (int i = 0; i < Inventory.size(); i++) {
            if (Inventory.get(i).id == id && Inventory.get(i).count < ITEMS.getItemById(Inventory.get(i).id).getItemConfiguration().maxCount) {
                int canadd = ITEMS.getItemById(Inventory.get(i).id).getItemConfiguration().maxCount - Inventory.get(i).count;
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
            int slotsCount = (count % ITEMS.getItemById(id).getItemConfiguration().maxCount) + 1;
            for (int i = 0; i < slotsCount; i++)
                Inventory.add(new InventorySlot());
        }
        for (int i = 0; i < Inventory.size(); i++) {
            if (Inventory.get(i).id == 0) {
                int canadd = ITEMS.getItemById(id).getItemConfiguration().maxCount;
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

    public Player() {
        this.playerBody = new Rectangle();
        this.playerBody.setSize(plW, plH);
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

        TextureRegion[][] tmp = TextureRegion.split(TEXTURES.player, TEXTURES.player.getWidth() / 4, TEXTURES.player.getHeight() / 3);
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

    public void render(Time WorldTime) {
        if (!isDead) {
            float AL = 0.75f - (Maths.module(720 - WorldTime.getTime()) / (720 / 0.3f));
            BATCH.setColor(1f, 1f, 1f, AL);
            BATCH.draw(TEXTURES.shadow, body.getPosition().x - playerBodyW / 2, body.getPosition().y - playerBodyH / 2 - playerBodyH / 4, plW, plW);
            BATCH.setColor(1f, 1f, 1f, 1f);
            BATCH.draw(currentFrame, body.getPosition().x - playerBodyW / 2, body.getPosition().y - playerBodyH / 2, plW, plH);
        }
    }

    public void update() {
        if (!isDead) {
            stateTime += Gdx.graphics.getDeltaTime();
            boolean isCameraZooming = false;
            for (int y = currentTileY - 3; y < currentTileY + 3; y++) {
                for (int x = currentTileX - 3; x < currentTileX + 3; x++) {
                    if (y > 0 && y < MAPS.map0.length && x > 0 && x < MAPS.map0[0].length) {
                        if (MAPS.map0[y][x][0].id == 15) {
                            GAME_CAMERA.setCameraZoom(0.75f, 0.025f);
                            isCameraZooming = true;
                        }
                    }
                }
            }
            if (!isCameraZooming) {
                GAME_CAMERA.setCameraZoom(1.25f, 0.025f);
            }

            weight = 0.0f;
            for (InventorySlot slot : Inventory) {
                weight += slot.count * ITEMS.getItemById(slot.id).getItemConfiguration().weight;
            }
            playerSpeed = 1.0f - ((weight * 1.66f) / 100);
            if (playerSpeed < 0) playerSpeed = 0;

            velX = 0;
            velY = 0;

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                velX = GAME_INTERFACE.j.normD(3).x;
                velY = GAME_INTERFACE.j.normD(3).y;
                if (GAME_INTERFACE.j.isTouched()) {
                    if (GAME_INTERFACE.j.angleI() < 315 && GAME_INTERFACE.j.angleI() > 225) {
                        rot = 0;
                    } else if (GAME_INTERFACE.j.angleI() < 225 && GAME_INTERFACE.j.angleI() > 135) {
                        rot = 1;
                    } else if (GAME_INTERFACE.j.angleI() > 45 && GAME_INTERFACE.j.angleI() < 135) {
                        rot = 2;
                    } else if (GAME_INTERFACE.j.angleI() < 45 || GAME_INTERFACE.j.angleI() > 315) {
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

            body.setLinearVelocity((float) (velocity.x * normalPlayerSpeed * 2) * SPEED_RATIO_X, (float) (velocity.y * normalPlayerSpeed * 2) * SPEED_RATIO_Y);
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
            currentTileX = (int) Math.ceil(body.getPosition().x / BLOCK_SIZE) - 1;
            currentTileY = (int) Math.ceil(body.getPosition().y / BLOCK_SIZE) - 1;

            playerBody.setPosition(posX, posY);
            velocity.x = 0;
            velocity.y = 0;

            for (InventorySlot inventorySlot : Inventory) {
                if (inventorySlot.data >= ITEMS.getItemById(inventorySlot.id).getItemConfiguration().maxData && ITEMS.getItemById(inventorySlot.id).getItemConfiguration().maxData != 0) {
                    slotsToRemove.add(inventorySlot);
                }
                if (inventorySlot.count <= 0) {
                    slotsToRemove.add(inventorySlot);
                }
            }
            if (slotsToRemove.size() > 0) {
                Inventory.removeAll(slotsToRemove);
                slotsToRemove.clear();
            }
            if (SETTINGS.autopickup || (GAME_INTERFACE.interactionButton.isTouched() || Gdx.input.isKeyPressed(Input.Keys.E))) {
                for (int i = 0; i < ENTITIES.size(); i++) {
                    if (ENTITIES.get(i) instanceof ItemEntity && Maths.distanceD((int) posX, (int) posY, ENTITIES.get(i).posX, ENTITIES.get(i).posY) < SCREEN_WIDTH / 32) {
                        ItemEntity item = (ItemEntity) ENTITIES.get(i);
                        addItem((int) item.getExtraData("itemId"), (int) item.getExtraData("itemCount"), (int) item.getExtraData("itemCount"));
                        ENTITIES.remove(i);
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
        achievementsChecker.update();
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

        public void setItem(int id, int count, int data) {
            this.id = id;
            this.count = count;
            this.data = data;
        }

        public void clear() {
            this.id = 0;
            this.count = 0;
            this.data = 0;
        }
    }
}
