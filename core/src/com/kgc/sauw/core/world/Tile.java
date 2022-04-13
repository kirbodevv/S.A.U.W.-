package com.kgc.sauw.core.world;

import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.block.LightBlock;
import com.kgc.sauw.core.entity.EntityManager;
import com.kgc.sauw.core.entity.entities.drop.Drop;
import com.kgc.sauw.core.gui.BlockInterface;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.utils.ExtraData;

import java.util.ArrayList;
import java.util.Random;

import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.core.world.WorldRenderer.rayHandler;

public class Tile implements com.intbyte.bdb.ExtraData {
    public static class TileEntityFactory implements ExtraDataFactory {
        @Override
        public com.intbyte.bdb.ExtraData getExtraData() {
            return new Tile();
        }
    }

    public int id;
    public int x, y, z;
    public int cx, cy, cz;
    public int damage;
    public int rotation = 0;
    public BlockInterface interface_ = null;
    public Rectangle blockRectangle;

    public ArrayList<ExtraData> extraData = new ArrayList<>();
    public ArrayList<Container> containers = new ArrayList<>();

    public PointLight PL;
    public Body body;
    private Block block;

    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("id", id);
        buffer.put("coords", new int[]{x, y, z});
        for (Container c : containers) {
            buffer.put(c.containerId, new int[]{c.getId(), c.getCount(), c.getDamage()});
        }
        return buffer.toBytes();
    }

    @Override
    public void readBytes(byte[] bytes, int begin, int end) {
        DataBuffer buffer = new DataBuffer();
        buffer.readBytes(bytes, begin, end);
        this.x = buffer.getIntArray("coords")[0];
        this.y = buffer.getIntArray("coords")[1];
        this.z = buffer.getIntArray("coords")[2];
        this.id = buffer.getInt("id");
        createTile(x, y, z, GameContext.getBlock(id));
        for (Container c : containers) {
            c.setItem(buffer.getIntArray(c.containerId)[0],
                    buffer.getIntArray(c.containerId)[1],
                    buffer.getIntArray(c.containerId)[2]);
        }
    }

    public void createTile(int x, int Y, int z, Block block) {
        this.x = x;
        this.y = Y;
        this.z = z;
        this.id = block.getId();
        this.blockRectangle = new Rectangle();
        this.blockRectangle.setPosition(x + block.getBlockConfiguration().getCollisionsRectangle().x, z + block.getBlockConfiguration().getCollisionsRectangle().y);
        this.blockRectangle.setSize(block.getBlockConfiguration().getCollisionsRectangle().width, block.getBlockConfiguration().getCollisionsRectangle().height);
        this.block = block;

        this.interface_ = this.block.GUI;
        if (interface_ != null)
            for (int i = 0; i < interface_.slots.size(); i++) {
                if (!interface_.slots.get(i).isInventorySlot) {
                    containers.add(new Container(interface_.slots.get(i).id));
                }
            }
        this.block.setDefaultExtraData(this);
        this.block.onPlace(this);

        damage = block.getBlockConfiguration().getMaxDamage();

    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setLight() {
        LightBlock block = (LightBlock) this.block;
        PL = new PointLight(rayHandler, 10, block.lightColor(), block.lightLevel(), x + 0.5f, y + 0.5f);
        PL.attachToBody(body);
        Gdx.app.log("Map", "Point light created");
    }

    public Container getContainer(String containerId) {
        for (Container container : containers) {
            if (container.containerId.equals(interface_.id + ".slot." + containerId)) {
                return container;
            }
        }
        return null;
    }

    public void setExtraData(String key, Object value) {
        for (ExtraData ED : extraData) {
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

    public Object getExtraData(String key) {
        for (ExtraData ED : extraData) {
            if (ED.key.equals(key)) {
                return ED.getValue();
            }
        }
        return null;
    }

    public void hit() {
        System.out.println(damage);
        damage -= 1;
    }

    public void update() {
        for (Container c : containers) {
            if (c.getId() == 0) {
                c.clear();
            }
        }
        if (damage <= 0 && !block.getFullId().equals("sauw:air")) {
            getWorld().map.setBlock(x, y, z, block.getBlockConfiguration().getBlockIdAfterDestroy());
            if (block.getBlockConfiguration().getDrop() != null) {
                for (int i = 0; i < block.getBlockConfiguration().getDrop().length; i++) {
                    Random r = new Random();
                    float xx = (r.nextFloat() - 0.5f) / 2f + x;
                    float yy = (r.nextFloat() - 0.5f) / 2f + y;
                    Drop drop = (Drop) EntityManager.spawn("entity:drop", xx, yy);
                    drop.setItem(block.getBlockConfiguration().getDrop()[i][0], block.getBlockConfiguration().getDrop()[i][1]);
                }
            }
        }
        block.tick(this);
    }

    public void render() {
        block.renderTick(this);
    }

    public void setBodyAndLight() {
        if (y == 0 && block.getBlockConfiguration().getCollisions())
            setBody(Physic.createRectangleBody(this.blockRectangle.x, this.blockRectangle.y, this.blockRectangle.width, this.blockRectangle.height, BodyDef.BodyType.StaticBody, false));
        if (y == 0 && block instanceof LightBlock) setLight();
    }

    public Block getBlock() {
        return block;
    }
}