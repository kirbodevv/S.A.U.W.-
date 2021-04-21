package com.kgc.sauw.map;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Input;
import com.kgc.sauw.UI.Container;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.environment.Blocks;
import com.kgc.sauw.environment.Environment;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.entity.ItemEntity;
import com.kgc.sauw.entity.Entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;
import java.util.Random;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraDataFactory;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.utils.ExtraData;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.entity.Entities.ENTITIES;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.GAME_CAMERA;
import static com.kgc.sauw.map.World.WORLD;

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
    public int biomId = 0;
    private int instrumentType;
    public Interface Interface = null;
    float timer;
    public TextureRegion t;
    public Rectangle block;
    private int WIDTH = Gdx.graphics.getWidth();

    public ArrayList<ExtraData> extraData = new ArrayList<ExtraData>();
    public ArrayList<Container> containers = new ArrayList<Container>();

    public PointLight PL;
    public Body body;

    @Override
    public byte[] getBytes() {
        DataBuffer buffer = new DataBuffer();
        buffer.put("id", id);
        buffer.put("coords", new int[]{x, y, z});
        for (Container c : containers) {
            buffer.put(c.ID, new int[]{c.getId(), c.getCount(), c.getData()});
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
        this.block.setPosition(X * WIDTH / 16 + bl.getBlockConfiguration().getCollisionsRectangle().x, Y * WIDTH / 16 + bl.getBlockConfiguration().getCollisionsRectangle().y);
        this.block.setSize(bl.getBlockConfiguration().getCollisionsRectangle().width, bl.getBlockConfiguration().getCollisionsRectangle().height);
        if (bl.t0 != null) t = TextureRegion.split(bl.t0, bl.t0.getWidth(), bl.t0.getHeight())[0][0];

        this.Interface = BLOCKS.getBlockById(id).GUI;
        /*if (Interface != null)
            for (int i = 0; i < Interface.slots.size(); i++) {
                if (!Interface.slots.get(i).isInventorySlot) {
                    containers.add(new Container(Interface.slots.get(i).ID));
                }
            }
*/
        BLOCKS.getBlockById(id).onPlace(this);

        damage = bl.getBlockConfiguration().getMaxDamage();
        instrumentType = bl.getBlockConfiguration().getInstrumentType();

    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setLight(RayHandler rh, Block bl) {
        if (bl.getBlockConfiguration().getLightingRadius() != -1) {
            PL = new PointLight(rh, 100, bl.getBlockConfiguration().getLightingColor(), bl.getBlockConfiguration().getLightingRadius() * WIDTH / 16, x * WIDTH / 16 + WIDTH / 32, y * WIDTH / 16 + WIDTH / 32);
            PL.attachToBody(body);
        }
    }

    public Container getContainer(String ID) {
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).ID.equals(ID)) {
                return containers.get(i);
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

    public int hit() {
        damage -= 1;
        return 1;

    }

    public void update(Entities Entities) {
        if (damage <= 0 && id != 4) {
            WORLD.setBlock(x, y, z, BLOCKS.getBlockById(id).getBlockConfiguration().getBlockIdAfterDestroy());
            if (BLOCKS.getBlockById(id).getBlockConfiguration().getDrop() != null) {
                for (int i = 0; i < BLOCKS.getBlockById(id).getBlockConfiguration().getDrop().length; i++) {
                    Random r = new Random();
                    int xx = r.nextInt(WIDTH / 16) + WIDTH / 16 * x;
                    int yy = r.nextInt(WIDTH / 16) + WIDTH / 16 * y;
                    Entities.spawn(new ItemEntity(xx, yy, BLOCKS.getBlockById(id).getBlockConfiguration().getDrop()[i][0], BLOCKS.getBlockById(id).getBlockConfiguration().getDrop()[i][1], 0));
                }
            }
        }
        BLOCKS.getBlockById(id).tick(this);
        if (Gdx.input.isTouched() && !GAME_INTERFACE.isTouched()) {
            double sc = (double) GAME_CAMERA.W / WIDTH;
            int cX = (int) (Gdx.input.getX() * sc + GAME_CAMERA.X);
            int cY = (int) (GAME_CAMERA.H - Gdx.input.getY() * sc + GAME_CAMERA.Y);
            int bX = (cX - (cX % (WIDTH / 16))) / (WIDTH / 16);
            int bY = (cY - (cY % (WIDTH / 16))) / (WIDTH / 16);
            if (bX == x && bY == y) {
                Environment.BLOCKS.getBlockById(id).click(this);
            }
        }
        if ((GAME_INTERFACE.interactionButton.wasClicked || Gdx.input.isKeyPressed(Input.Keys.E)) && Maths.distance(x, y, PLAYER.currentTileX, PLAYER.currentTileY) <= 1.5 && ((PLAYER.currentTileY + 1 == y && PLAYER.rot == 0) || (PLAYER.currentTileX + 1 == x && PLAYER.rot == 1) || (PLAYER.currentTileY - 1 == y && PLAYER.rot == 2) || (PLAYER.currentTileX - 1 == x && PLAYER.rot == 3))) {
            Environment.BLOCKS.getBlockById(id).onInteractionButtonPressed(this);
            if (Interface != null)
                if (!Interface.isOpen)
                    Interface.open(x, y, z);
        }

    }
}