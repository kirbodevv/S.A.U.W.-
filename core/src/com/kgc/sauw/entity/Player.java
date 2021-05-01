package com.kgc.sauw.entity;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.Inventory;
import com.kgc.sauw.InventoryContainer;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.items.Item;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.physic.Physic;

import java.util.Random;

import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.entity.Entities.ENTITIES_LIST;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.map.World.WORLD;
import static com.kgc.sauw.ui.interfaces.Interfaces.DEAD_INTERFACE;
import static com.kgc.sauw.ui.interfaces.Interfaces.GAME_INTERFACE;


public class Player extends Entity implements ExtraData {
    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("health", health);
        buffer.put("hunger", hunger);
        buffer.put("coords", new int[]{(int) getPosition().x, (int) getPosition().y});
        buffer.put("InvLenght", Inventory.containers.size());
        for (int i = 0; i < Inventory.containers.size(); i++) {
            buffer.put("Inv_" + i, Inventory.containers.get(i));
        }
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes);
        setPosition(buffer.getIntArray("coords")[0], buffer.getIntArray("coords")[1]);
        health = buffer.getInt("health");
        hunger = buffer.getInt("hunger");
        Inventory = new Inventory(buffer.getInt("InvLenght"));
        for (int i = 0; i < buffer.getInt("InvLenght"); i++) {
            Inventory.containers.add(i, new InventoryContainer());
            Inventory.containers.get(i).readBytes(buffer.getByteArray("Inv_" + i), begin, end);
        }
    }

    public int carriedSlot = 0;
    public int[] hotbar = new int[8];

    public Item getCarriedItem() {
        return getItemFromHotbar(carriedSlot);
    }

    public Item getItemFromHotbar(int index) {
        if (hotbar[index] != -1 && hotbar[index] < Inventory.containers.size() && ITEMS.getItemById(Inventory.containers.get(hotbar[index]).id) != null) {
            return ITEMS.getItemById(Inventory.containers.get(hotbar[index]).id);
        } else {
            hotbar[index] = -1;
            return ITEMS.getItemById(0);
        }
    }

    @Override
    public void onDead() {
        DEAD_INTERFACE.open();
    }

    public void spawn() {
        if (isDead) {
            Random r = new Random();
            currentTileY = 4;//r.nextInt(MAPS.map0.length - 2) + 1;
            currentTileX = 4;//r.nextInt(MAPS.map0[0].length - 2) + 1;
            setPosition(currentTileX * BLOCK_SIZE, currentTileY * BLOCK_SIZE);
        }
        isDead = false;
        health = maxHealth;
    }

    public Player() {
        entityBodyW = (int) (BLOCK_SIZE * 10 / 26f);
        entityBodyH = BLOCK_SIZE;
        setSize(new Vector2(entityBodyW, entityBodyH));
        setBody(Physic.createRectangleBody(0, 0, entityBodyW, entityBodyH / 4f, BodyDef.BodyType.DynamicBody));

        for (int i = 0; i < hotbar.length; i++) {
            this.hotbar[i] = -1;
        }

        animator = new Animator(TEXTURES.player, 4, 3);

        animator.addAnimation("animation:player_walk_left", 0.2f, 0, 1);
        animator.addAnimation("animation:player_walk_right", 0.2f, 2, 3);
        animator.addAnimation("animation:player_walk_down", 0.2f, 4, 5);
        animator.addAnimation("animation:player_walk_up", 0.2f, 6, 7);

        currentFrame = animator.getFrames()[4];

        spawn();
    }

    @Override
    public void render() {
        if (!isDead) {
            float AL = 0.75f - (Maths.module(720 - WORLD.WorldTime.getTime()) / (720 / 0.3f));
            BATCH.setColor(1f, 1f, 1f, AL);
            //BATCH.draw(TEXTURES.shadow, body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 2f, entityBodyW, entityBodyH);
            BATCH.setColor(1f, 1f, 1f, 1f);
            BATCH.draw(currentFrame, body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 8f, entityBodyW, entityBodyH);
        }
    }

    @Override
    public void tick() {
        if (!isDead) {
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

            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                velX = GAME_INTERFACE.j.normD(3).x;
                velY = GAME_INTERFACE.j.normD(3).y;
                if (GAME_INTERFACE.j.isTouched()) {
                    if (GAME_INTERFACE.j.angleI() < 315 && GAME_INTERFACE.j.angleI() > 225) {
                        rotation = 0;
                    } else if (GAME_INTERFACE.j.angleI() < 225 && GAME_INTERFACE.j.angleI() > 135) {
                        rotation = 1;
                    } else if (GAME_INTERFACE.j.angleI() > 45 && GAME_INTERFACE.j.angleI() < 135) {
                        rotation = 2;
                    } else if (GAME_INTERFACE.j.angleI() < 45 || GAME_INTERFACE.j.angleI() > 315) {
                        rotation = 3;
                    }
                }
            } else if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    velY = 1;
                    rotation = 0;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    velY = -1;
                    rotation = 2;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    velX = -1;
                    rotation = 3;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    velX = 1;
                    rotation = 1;
                }
            }
            if (rotation == 0) {
                currentFrame = animator.getFrame("animation:player_walk_up");
            } else if (rotation == 1) {
                currentFrame = animator.getFrame("animation:player_walk_right");
            } else if (rotation == 2) {
                currentFrame = animator.getFrame("animation:player_walk_down");
            } else if (rotation == 3) {
                currentFrame = animator.getFrame("animation:player_walk_left");
            }

            if (!isEntityMoving()) {
                if (rotation == 1) {
                    currentFrame = animator.getFrames()[3];
                } else if (rotation == 3) {
                    currentFrame = animator.getFrames()[1];
                } else if (rotation == 0) {
                    currentFrame = animator.getFrames()[9];
                } else if (rotation == 2) {
                    currentFrame = animator.getFrames()[8];
                }
            }
            if (SETTINGS.autopickup || (GAME_INTERFACE.interactionButton.isTouched() || Gdx.input.isKeyPressed(Input.Keys.E))) {
                for (int i = 0; i < ENTITIES_LIST.size(); i++) {
                    if (ENTITIES_LIST.get(i) instanceof ItemEntityL && Maths.distanceD((int) getPosition().x, (int) getPosition().x, ENTITIES_LIST.get(i).posX, ENTITIES_LIST.get(i).posY) < SCREEN_WIDTH / 32) {
                        ItemEntityL item = (ItemEntityL) ENTITIES_LIST.get(i);
                        Inventory.addItem((int) item.getExtraData("itemId"), (int) item.getExtraData("itemCount"));
                        ENTITIES_LIST.remove(i);
                    }
                }
            }
        }
    }
}