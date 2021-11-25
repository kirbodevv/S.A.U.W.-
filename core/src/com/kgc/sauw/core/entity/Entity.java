package com.kgc.sauw.core.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.world.Map;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.utils.ExtraData;

import java.util.ArrayList;
import java.util.Random;

import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;

public abstract class Entity {
    private int id;

    private final AbstractEntityRenderer entityRenderer;

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

    public ArrayList<com.kgc.sauw.core.utils.ExtraData> extraData = new ArrayList<>();


    public Entity() {
        entityRenderer = getEntityRenderer();
    }

    protected void setBody(Body body) {
        this.body = body;
        this.body.setFixedRotation(true);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void spawn(int x, int y) {
        setPosition(x, y);
        isDead = false;
    }

    public void randomSpawn() {
        Random r = new Random();
        int x = r.nextInt(Map.xSize - 2) + 1;
        int y = r.nextInt(Map.ySize - 2) + 1;
        spawn(x, y);

    }

    public Vector2 getBodySize() {
        return new Vector2(1, 1);
    }

    public Block stayingOn() {
        return GameContext.getBlock(getWorld().map.getTile(currentTileX, currentTileY, 1).id);
    }

    private void updatePosition() {
        currentTileX = (int) Math.ceil(body.getPosition().x) - 1;
        currentTileY = (int) Math.ceil(body.getPosition().y) - 1;

        bodyRectangle.setPosition(body.getPosition().x - entityBodyW / 2f, body.getPosition().y - entityBodyH / 2f);
        position.set(bodyRectangle.x, bodyRectangle.y);
    }

    public void update() {
        velocity.x = 0;
        velocity.y = 0;
        updatePosition();

        velX = 0;
        velY = 0;

        tick();

        velocity.x = (velX * (entitySpeed));
        velocity.y = (velY * (entitySpeed));

        body.setLinearVelocity((velocity.x * normalEntitySpeed), (velocity.y * normalEntitySpeed));
    }

    public void setVelocity(float velX, float velY) {
        this.velX = velX;
        this.velY = velY;
    }

    public void setVelocityX(float velX) {
        this.velX = velX * 1.5f;
    }

    public void setVelocityY(float velY) {
        this.velY = velY * 1.5f;
    }

    public Vector2 getPosition() {
        updatePosition();
        return position;
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

    public void setExtraData(String key, Object value) {
        for (com.kgc.sauw.core.utils.ExtraData ED : extraData) {
            if (ED.key.equals(key)) {
                ED.setValue(value);
                return;
            }
        }
        extraData.add(new ExtraData(key));
        for (ExtraData ED : extraData) {
            if (ED.key.equals(key)) {
                ED.setValue(value);
                return;
            }
        }
    }

    public int getMoveAngle() {
        return Maths.angleBetweenVectors(0, 0, velocity.x, velocity.y);
    }

    public float getWidth() {
        return entityBodyW;
    }

    public float getHeight() {
        return entityBodyH;
    }

    public abstract void tick();

    public void render() {
        if (entityRenderer != null)
            entityRenderer.render(this, BATCH);
    }

    public abstract AbstractEntityRenderer getEntityRenderer();

    public Object getExtraData(String key) {
        for (ExtraData ED : extraData) {
            if (ED.key.equals(key)) {
                return ED.getValue();
            }
        }
        return null;
    }

    public void loadExtraData(DataBuffer buffer) {
        for (com.kgc.sauw.core.utils.ExtraData data : extraData) {
            if (data.getValue() instanceof Integer) {
                data.setValue(buffer.getInt(data.key));
            } else if (data.getValue() instanceof Float) {
                data.setValue(buffer.getFloat(data.key));
            } else if (data.getValue() instanceof Double) {
                data.setValue(buffer.getDouble(data.key));
            } else if (data.getValue() instanceof Short) {
                data.setValue(buffer.getShort(data.key));
            } else if (data.getValue() instanceof Long) {
                data.setValue(buffer.getLong(data.key));
            } else if (data.getValue() instanceof Byte) {
                data.setValue(buffer.getByte(data.key));
            }
        }
    }

    public static class EntityLoaderFactory implements ExtraDataFactory {
        @Override
        public com.intbyte.bdb.ExtraData getExtraData() {
            return new EntityLoader();
        }
    }

    public static class EntityLoader implements com.intbyte.bdb.ExtraData {

        @Override
        public byte[] getBytes() {
            return new byte[0];
        }

        @Override
        public void readBytes(byte[] bytes, int begin, int end) {
            DataBuffer buffer = new DataBuffer();
            buffer.readBytes(bytes, begin, end);
            try {
                int id = buffer.getInt("id");
                Vector2 position = new Vector2(buffer.getFloat("X"), buffer.getFloat("Y"));

                Entity entity = EntityManager.spawn(id, position.x, position.y);
                entity.loadExtraData(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class EntitySaver implements com.intbyte.bdb.ExtraData {
        Entity entity;

        public EntitySaver(Entity entity) {
            this.entity = entity;
        }

        @Override
        public byte[] getBytes() {
            DataBuffer buffer = new DataBuffer();
            buffer.put("X", entity.position.x);
            buffer.put("Y", entity.position.y);
            buffer.put("id", entity.id);
            for (com.kgc.sauw.core.utils.ExtraData data : entity.extraData) {
                if (data.getValue() instanceof Integer) {
                    buffer.put(data.key, (int) data.getValue());
                } else if (data.getValue() instanceof Float) {
                    buffer.put(data.key, (float) data.getValue());
                } else if (data.getValue() instanceof Double) {
                    buffer.put(data.key, (double) data.getValue());
                } else if (data.getValue() instanceof Short) {
                    buffer.put(data.key, (short) data.getValue());
                } else if (data.getValue() instanceof Long) {
                    buffer.put(data.key, (long) data.getValue());
                } else if (data.getValue() instanceof Byte) {
                    buffer.put(data.key, (byte) data.getValue());
                }
            }
            return buffer.toBytes();
        }

        @Override
        public void readBytes(byte[] bytes, int i, int i1) {

        }
    }
}
