package com.kgc.sauw.core.map;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import com.kgc.sauw.core.entity.Drop;
import com.kgc.sauw.core.entity.EntityManager;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.core.utils.ExtraData;

import java.util.ArrayList;
import java.util.Random;

import static com.kgc.sauw.core.environment.Environment.BLOCKS;
import static com.kgc.sauw.core.environment.Environment.ITEMS;
import static com.kgc.sauw.core.map.World.WORLD;

public class Tile implements com.intbyte.bdb.ExtraData {
    public static class TileEntityFactory implements ExtraDataFactory {
        @Override
        public com.intbyte.bdb.ExtraData getExtraData() {
            return new Tile();
        }
    }

    public int id;
    public int x, y, z;
    public int damage;
    public Interface Interface = null;
    public TextureRegion t;
    public Rectangle block;

    public ArrayList<ExtraData> extraData = new ArrayList<>();
    public ArrayList<Container> containers = new ArrayList<>();

    public PointLight PL;
    public Body body;

    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("id", id);
        buffer.put("coords", new int[]{x, y, z});
        for (Container c : containers) {
            buffer.put(c.ID, new int[]{c.getId(), c.getCount(), c.getDamage()});
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
        createTile(x, y, z, BLOCKS.getBlockById(id));
        for (Container c : containers) {
            c.setItem(buffer.getIntArray(c.ID)[0],
                    buffer.getIntArray(c.ID)[1],
                    buffer.getIntArray(c.ID)[2]);
        }
    }

    public void createTile(int X, int Y, int Z, Block bl) {
        this.x = X;
        this.y = Y;
        this.z = Z;
        this.id = bl.id;
        this.block = new Rectangle();
        this.block.setPosition(X + bl.getBlockConfiguration().getCollisionsRectangle().x, Y + bl.getBlockConfiguration().getCollisionsRectangle().y);
        this.block.setSize(bl.getBlockConfiguration().getCollisionsRectangle().width, bl.getBlockConfiguration().getCollisionsRectangle().height);

        if (bl.t0 != null) t = TextureRegion.split(bl.t0, bl.t0.getWidth(), bl.t0.getHeight())[0][0];

        this.Interface = BLOCKS.getBlockById(id).GUI;
        if (Interface != null)
            for (int i = 0; i < Interface.slots.size(); i++) {
                if (!Interface.slots.get(i).isInventorySlot) {
                    containers.add(new Container(Interface.slots.get(i).ID));
                }
            }
        BLOCKS.getBlockById(id).onPlace(this);

        damage = bl.getBlockConfiguration().getMaxDamage();

    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setLight(RayHandler rh, Block bl) {
        if (bl.getBlockConfiguration().getLightingRadius() != -1) {
            PL = new PointLight(rh, 100, bl.getBlockConfiguration().getLightingColor(), bl.getBlockConfiguration().getLightingRadius(), x + 0.5f, y + 0.5f);
            PL.attachToBody(body);
        }
    }

    public Container getContainer(String ID) {
        for (Container container : containers) {
            if (container.ID.equals(ID)) {
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
        damage -= 1;

    }

    public void update() {
        for (Container c : containers) {
            if (c.getId() != 0 && ITEMS.getItemById(c.getId()).id == 0) {
                c.setItem(0, 0, 0);
            }
        }
        if (damage <= 0 && id != 4) {
            WORLD.setBlock(x, y, z, BLOCKS.getBlockById(id).getBlockConfiguration().getBlockIdAfterDestroy());
            if (BLOCKS.getBlockById(id).getBlockConfiguration().getDrop() != null) {
                for (int i = 0; i < BLOCKS.getBlockById(id).getBlockConfiguration().getDrop().length; i++) {
                    Random r = new Random();
                    float xx = (r.nextFloat() - 0.5f) / 2f + x;
                    float yy = (r.nextFloat() - 0.5f) / 2f + y;
                    Drop drop = (Drop) EntityManager.spawn("entity:drop", xx, yy);
                    drop.setItem(BLOCKS.getBlockById(id).getBlockConfiguration().getDrop()[i][0], BLOCKS.getBlockById(id).getBlockConfiguration().getDrop()[i][1]);
                }
            }
        }
        BLOCKS.getBlockById(id).tick(this);
        /*if (Gdx.input.isTouched() && !GAME_INTERFACE.isTouched()) {
            double sc = (double) GAME_CAMERA.W / SCREEN_WIDTH;
            int cX = (int) (Gdx.input.getX() * sc + GAME_CAMERA.X);
            int cY = (int) (GAME_CAMERA.H - Gdx.input.getY() * sc + GAME_CAMERA.Y);
            int bX = (cX - (cX % BLOCK_SIZE)) / BLOCK_SIZE;
            int bY = (cY - (cY % BLOCK_SIZE)) / BLOCK_SIZE;
            if (bX == x && bY == y) {
                Environment.BLOCKS.getBlockById(id).click(this);
            }
        }*/
    }

    public void render() {
        BLOCKS.getBlockById(id).renderTick(this);
    }
}