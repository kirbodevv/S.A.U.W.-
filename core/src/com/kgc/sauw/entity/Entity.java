package com.kgc.sauw.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kgc.sauw.Inventory;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.graphic.Animator;

import java.util.Random;

import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.map.World.MAPS;

public class Entity {
    public Inventory Inventory = new Inventory();

    public float maxWeight = 40.0f;
    public float itemsWeight = 0.0f;

    public int maxHealth = 20;
    public int health = 20;
    public int hunger = 0;

    protected Animator animator;
    protected TextureRegion currentFrame;

    protected float velX;
    protected float velY;
    protected int currentTileX, currentTileY;
    protected float entityBodyW;
    protected float entityBodyH;

    protected Vector2 velocity = new Vector2(0, 0);
    protected Rectangle bodyRectangle = new Rectangle();
    protected Body body;

    private final Vector2 position = new Vector2();
    private Vector2 size = new Vector2();

    public int rotation = 0;

    public final float normalEntitySpeed = 2f;
    public float entitySpeed = 1.0f;

    protected boolean isDead = false;

    protected void setBody(Body body) {
        this.body = body;
        this.body.setFixedRotation(true);
    }

    public void spawn(int x, int y) {
        setPosition(x, y);
        isDead = false;
        health = maxHealth;
    }

    public void randomSpawn() {
        Random r = new Random();
        int x = r.nextInt(MAPS.map0.length - 2) + 1;
        int y = r.nextInt(MAPS.map0[0].length - 2) + 1;
        spawn(x, y);

    }

    public Block stayingOn() {
        return BLOCKS.getBlockById(MAPS.map0[currentTileY][currentTileX][1].id);
    }

    private void updatePosition() {
        currentTileX = (int) Math.ceil(body.getPosition().x) - 1;
        currentTileY = (int) Math.ceil(body.getPosition().y) - 1;

        bodyRectangle.setPosition(body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 8f);
        position.set(bodyRectangle.x, bodyRectangle.y);
    }

    public void update() {
        itemsWeight = Inventory.getItemsWeight();

        entitySpeed = 1.0f - ((itemsWeight * 1.66f) / 100);
        if (entitySpeed < 0) entitySpeed = 0;

        updatePosition();

        Inventory.removeItemsIfNeed();

        velX = 0;
        velY = 0;

        tick();

        velocity.x = (velX * (entitySpeed));
        velocity.y = (velY * (entitySpeed));

        animationTick();

        body.setLinearVelocity((velocity.x * normalEntitySpeed), (velocity.y * normalEntitySpeed));

        velocity.x = 0;
        velocity.y = 0;
    }

    public void tick() {

    }

    public void animationTick() {

    }

    public void render() {

    }

    public void setVelocity(float velX, float velY) {
        this.velX = velX;
        this.velY = velY;
    }

    public void setVelocityX(float velX) {
        this.velX = velX;
    }

    public void setVelocityY(float velY) {
        this.velY = velY;
    }

    public Vector2 getPosition() {
        updatePosition();
        return position;
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) kill();
    }

    public void kill() {
        isDead = true;
    }

    public void onDead() {

    }

    public Vector2 getSize() {
        return size;
    }

    protected void setSize(Vector2 size) {
        bodyRectangle.setSize(size.x, size.y);
        this.size = size;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }

    public int getCurrentTileX() {
        return currentTileX;
    }

    public int getCurrentTileY() {
        return currentTileY;
    }

    public boolean isEntityMoving() {
        return velX != 0 || velY != 0;
    }


    /*@Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("coords", new float[]{position.x, position.y});
        buffer.put("type", type);
        for (com.kgc.sauw.utils.ExtraData data : ExtraData) {
            if (Integer.class.isInstance(data.getValue())) {
                buffer.put(data.key, (int)data.getValue());
            } else if (Float.class.isInstance(data.getValue())) {
                buffer.put(data.key, (float)data.getValue());
            } else if (Double.class.isInstance(data.getValue())) {
                buffer.put(data.key, (double)data.getValue());
            } else if (Short.class.isInstance(data.getValue())) {
                buffer.put(data.key, (short)data.getValue());
            } else if (Long.class.isInstance(data.getValue())) {
                buffer.put(data.key, (long)data.getValue());
            } else if (Byte.class.isInstance(data.getValue())) {
                buffer.put(data.key, (byte)data.getValue());
            }
        }
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes, begin, end);
        posX = buffer.getIntArray("coords")[0];
        posY = buffer.getIntArray("coords")[1];
        type = buffer.getInt("type");
        loadedEntity = createMob(type);
        loadedEntity.loadExtraData(bytes, begin, end);
    }
    public void loadExtraData(byte[] bytes, int begin, int end){
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes, begin, end);
        for (com.kgc.sauw.utils.ExtraData data : ExtraData) {
            if (Integer.class.isInstance(data.getValue())) {
                data.setValue(buffer.getInt(data.key));
            } else if (Float.class.isInstance(data.getValue())) {
                data.setValue(buffer.getFloat(data.key));
            } else if (Double.class.isInstance(data.getValue())) {
                data.setValue(buffer.getDouble(data.key));
            } else if (Short.class.isInstance(data.getValue())) {
                data.setValue(buffer.getShort(data.key));
            } else if (Long.class.isInstance(data.getValue())) {
                data.setValue(buffer.getLong(data.key));
            } else if (Byte.class.isInstance(data.getValue())) {
                data.setValue(buffer.getByte(data.key));
            }
        }
    }
    public static class MobFactory implements ExtraDataFactory {
        @Override
        public com.intbyte.bdb.ExtraData getExtraData() {
            return new Entity();
        }
    }*/
}
