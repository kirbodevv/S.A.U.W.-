package com.kgc.sauw.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kgc.sauw.Inventory;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.particle.Particles;

import java.util.Random;

import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.map.World.MAPS;

public class Entity {
    protected static float SPEED_RATIO_X;
    protected static float SPEED_RATIO_Y;

    static {
        SPEED_RATIO_X = Gdx.graphics.getWidth() / 1280.0f;
        SPEED_RATIO_Y = Gdx.graphics.getHeight() / 720.0f;
    }


    public Inventory Inventory = new Inventory();

    public float maxWeight = 40.0f;
    public float itemsWeight = 0.0f;

    public int maxHealth = 20;
    public int health = 20;
    public int hunger = 0;

    protected Animator animator;
    protected TextureRegion currentFrame;

    protected double velX;
    protected double velY;
    protected int currentTileX, currentTileY;
    protected int entityBodyW;
    protected int entityBodyH;

    protected Vector2 velocity = new Vector2(0, 0);
    protected Rectangle bodyRectangle = new Rectangle();
    protected Body body;

    private Vector2 position = new Vector2();
    private Vector2 size = new Vector2();

    public int rotation = 0;

    public final float normalEntitySpeed = SCREEN_WIDTH / 16;
    public float entitySpeed = 1.0f;

    protected boolean isDead = false;

    protected void setBody(Body body) {
        this.body = body;
        this.body.setFixedRotation(true);
    }

    public void randomSpawn(int x, int y) {
        setPosition(x * BLOCK_SIZE, y * BLOCK_SIZE);
        isDead = false;
        health = maxHealth;
    }

    public void randomSpawn() {
        Random r = new Random();
        int x = r.nextInt(MAPS.map0.length - 2) + 1;
        int y = r.nextInt(MAPS.map0[0].length - 2) + 1;
        randomSpawn(x, y);

    }

    public Block stayingOn() {
        return BLOCKS.getBlockById(MAPS.map0[currentTileY][currentTileX][1].id);
    }

    private void updatePosition() {
        currentTileX = (int) Math.ceil(body.getPosition().x / BLOCK_SIZE) - 1;
        currentTileY = (int) Math.ceil(body.getPosition().y / BLOCK_SIZE) - 1;

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

        velocity.x = (float) (velX * (entitySpeed));
        velocity.y = (float) (velY * (entitySpeed));

        animationTick();

        body.setLinearVelocity((velocity.x * normalEntitySpeed * 2) * SPEED_RATIO_X, (velocity.y * normalEntitySpeed * 2) * SPEED_RATIO_Y);

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
}
