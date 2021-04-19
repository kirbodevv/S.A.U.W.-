package com.kgc.sauw.map;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.intbyte.bdb.DataBuffer;
import com.kgc.sauw.Achievements;
import com.kgc.sauw.Modding.Mods;
import com.kgc.sauw.entity.Entities;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.Time;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.math.Maths;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.UI.Interfaces.Interfaces.isAnyInterfaceOpen;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.*;

public class World {
    public static final Maps MAPS;
    public static final World WORLD;

    static {
        MAPS = new Maps();
        WORLD = new World();
    }

    private int WIDTH = Gdx.graphics.getWidth();
    private int HEIGHT = Gdx.graphics.getHeight();

    private boolean interfaceTouched;
    private boolean isTouched;
    private Random r = new Random();
    private boolean worldTouched;
    public Time WorldTime;

    public com.badlogic.gdx.physics.box2d.World world;
    public RayHandler RayHandler;
    public Entities entities;

    String WorldName = null;
    float TL = 720 / 0.6f;

    public void save(String WorldName) {
        this.WorldName = WorldName;
        FileHandle worldFolder = Gdx.files.external("S.A.U.W./Worlds/" + WorldName);
        FileHandle map = Gdx.files.external(worldFolder + "/map");

        if (!worldFolder.exists()) worldFolder.mkdirs();
        if (!map.exists()) map.mkdirs();

        try {

            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/world.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle mobsFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/mobs.bdb");

            if (!mapFile.exists()) mapFile.file().createNewFile();
            if (!worldData.exists()) worldData.file().createNewFile();
            if (!playerFile.exists()) playerFile.file().createNewFile();
            if (!mapFile.exists()) mobsFile.file().createNewFile();

            DataBuffer playerBuffer = new DataBuffer();
            DataBuffer worldDataBuffer = new DataBuffer();
            playerBuffer.put("player", PLAYER);
            worldDataBuffer.put("time", WorldTime.getTime());
            Date date = new Date();
            worldDataBuffer.put("saveTime", date.getTime());

            playerFile.writeBytes(playerBuffer.toBytes(), false);
            mobsFile.writeBytes(this.entities.getBytes(), false);
            worldData.writeBytes(worldDataBuffer.toBytes(), false);
            mapFile.writeBytes(MAPS.toDataBuffer().toBytes(), false);
        } catch (Exception e) {
            Gdx.app.log("saveError", e.toString());
        }
    }

    public void save() {
        if (WorldName != null) {
            save(WorldName);
        }
    }

    public void createNewWorld() {
        MAPS.generateWorld(this, ITEMS);
    }

    public void load(String WorldName) {
        this.WorldName = WorldName;
        try {
            PLAYER.data.put("lastWorld", WorldName);
            PLAYER.saveData();
        } catch (Exception e) {
        }
        FileHandle worldFolder = Gdx.files.external("/S.A.U.W./Worlds/" + WorldName);
        if (worldFolder.exists()) {
            FileHandle mapFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/map/map.bdb");
            FileHandle worldData = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/world.bdb");
            FileHandle playerFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/player.bdb");
            FileHandle mobsFile = Gdx.files.external("S.A.U.W./Worlds/" + WorldName + "/mobs.bdb");
            if (mapFile.exists()) {
                DataBuffer buffer = new DataBuffer();
                buffer.readBytes(mapFile.readBytes());
                int[] map = buffer.getIntArray("mapIds");
                int[] mapDmg = buffer.getIntArray("mapDamage");
                Tile.TileEntityFactory TEF = new Tile.TileEntityFactory();
                List<? extends com.intbyte.bdb.ExtraData> tileEntitys = null;
                if (buffer.getInt("tileEnCount") > 0) {
                    tileEntitys = buffer.getExtraDataList("tileEntitys", TEF);
                }
                int i = 0;
                for (int y = 0; y < MAPS.map0.length; y++) {
                    for (int x = 0; x < MAPS.map0[y].length; x++) {
                        for (int z = 0; z < MAPS.map0[y][x].length; z++) {
                            if (buffer.getInt("tileEnCount") > 0) {
                                for (int j = 0; j < tileEntitys.size(); j++) {
                                    Tile tile = (Tile) tileEntitys.get(j);
                                    if (x == tile.x && y == tile.y && z == tile.z) {
                                        setBlock(tile);
                                    }
                                }
                            }
                            if (MAPS.map0[y][x][z] == null)
                                setBlock(x, y, z, map[i]);
                            MAPS.map0[y][x][z].damage = mapDmg[i];
                            i++;
                        }
                    }
                }
                buffer.readBytes(playerFile.readBytes());
                if (PLAYER != null)
                    PLAYER.readBytes(buffer.getByteArray("player"), 0, buffer.getByteArray("player").length);
                byte[] mobsBytes = mobsFile.readBytes();
                if (entities != null) entities.readBytes(mobsBytes, 0, mobsBytes.length);
                buffer.readBytes(worldData.readBytes());
                if (WorldTime != null) WorldTime.setTime(buffer.getInt("time"));
            }
        }
    }

    public void screenshot(String screenshotName) {
        Pixmap pixmap = new Pixmap(32 * 40, 32 * 40, Pixmap.Format.RGBA8888);
        for (int y = MAPS.map0.length - 1; y >= 0; y--) {
            for (int x = 0; x < MAPS.map0[y].length; x++) {
                for (int z = MAPS.map0[y][x].length - 3; z >= 0; z--) {
                    Tile t = MAPS.map0[y][x][z];
                    Block b = BLOCKS.getBlockById(t.id);
                    if (t.t != null) {
                        Pixmap p = extractPixmapFromTextureRegion(t.t);
                        pixmap.drawPixmap(p, 0, 0, p.getWidth(), p.getHeight(), x * 32, y * 32, 32 * b.getBlockConfiguration().getSize().x, 32 * b.getBlockConfiguration().getSize().y);
                        p.dispose();
                    }
                }
            }
        }
        FileHandle png = Gdx.files.external("S.A.U.W./Screenshots/" + screenshotName + ".png");
        PixmapIO.writePNG(png, pixmap);
        pixmap.dispose();
    }

    public Pixmap extractPixmapFromTextureRegion(TextureRegion textureRegion) {
        TextureData textureData = textureRegion.getTexture().getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        textureData.disposePixmap();
        Pixmap pixmap = new Pixmap(
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                textureData.getFormat()
        );
        pixmap.drawPixmap(
                textureData.consumePixmap(),
                0,
                0,
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight()
        );
        return pixmap;
    }

    public World() {
        createWorld();
        entities = new Entities(MAPS, TEXTURES);
        PLAYER.body = createBox(PLAYER.posX, PLAYER.posY, PLAYER.playerBodyW, PLAYER.playerBodyH, BodyDef.BodyType.DynamicBody);
        PLAYER.body.setFixedRotation(true);
    }

    public void createWorld() {
        this.WorldTime = new Time();
        world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), false);
        RayHandler = new RayHandler(world);
        RayHandler.setAmbientLight(1, 1, 1, 1);
        RayHandler.useDiffuseLight(true);
    }

    public Body createBox(float posX, float posY, float boxW, float boxH, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(posX + boxW / 2.f, posY + boxH / 2.f);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxW / 2.f, boxH / 2.f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public boolean setBlock(int x, int y, int z, Block block) {
        if (x >= 0 && x < MAPS.map0[0].length + 1 && y >= 0 && y < MAPS.map0.length + 1) {
            Tile tile = new Tile();
            tile.createTile(x, y, z, block);
            if (MAPS.map0[y][x][z] != null && MAPS.map0[y][x][z].body != null)
                world.destroyBody(MAPS.map0[y][x][z].body);
            MAPS.map0[y][x][z] = tile;
            setBodyAndLight(x, y, z, tile, block);
            return true;
        }
        return false;
    }

    public void setBodyAndLight(int x, int y, int z, Tile tile, Block block) {
        if (block.id != 4 && z == 0)
            tile.setBody(createBox((int) tile.block.x, (int) tile.block.y, (int) tile.block.width, (int) tile.block.height, BodyDef.BodyType.StaticBody));
        if (z == 0) tile.setLight(RayHandler, block);
    }

    public boolean setBlock(Tile tile) {
        if (MAPS.map0[tile.y][tile.x][tile.z] != null && MAPS.map0[tile.y][tile.x][tile.z].body != null)
            world.destroyBody(MAPS.map0[tile.y][tile.x][tile.z].body);
        MAPS.map0[tile.y][tile.x][tile.z] = tile;
        setBodyAndLight(tile.x, tile.y, tile.z, tile, BLOCKS.getBlockById(tile.id));
        return true;
    }

    public boolean setBlock(int x, int y, int id) {
        int z;
        if (MAPS.map0[y][x][1].id == 4) {
            z = 1;
        } else if (MAPS.map0[y][x][0].id == 4) {
            z = 0;
        } else {
            return false;
        }
        return setBlock(x, y, z, BLOCKS.getBlockById(id));
    }

    public boolean setBlock(int x, int y, int z, int id) {
        return setBlock(x, y, z, BLOCKS.getBlockById(id));
    }

    public int getHighestBlock(int x, int y) {
        for (int z = 0; z < MAPS.map0[y][x].length; z++) {
            if (MAPS.map0[y][x][z].id != 4) {
                return z;
            }
        }
        return -1;
    }

    public void update(Mods mods, Achievements a) {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        PLAYER.update(this, a, GAME_CAMERA);
        entities.update();
        MAPS.update(entities);
        if (Gdx.input.isTouched()) {
            if (!isTouched) {
                if (GAME_INTERFACE.isTouched())
                    interfaceTouched = true;
                else worldTouched = true;

                isTouched = true;
            }
        }
        if (!Gdx.input.isTouched() && interfaceTouched)
            interfaceTouched = false;
        if (!Gdx.input.isTouched() && worldTouched) {
            worldTouched = false;
            if (!interfaceTouched && !isAnyInterfaceOpen()) {
                double sc = (double) GAME_CAMERA.W / WIDTH;
                int cX = (int) (Gdx.input.getX() * sc + GAME_CAMERA.X);
                int cY = (int) (GAME_CAMERA.H - Gdx.input.getY() * sc + GAME_CAMERA.Y);
                int bX = (cX - (cX % (WIDTH / 16))) / (WIDTH / 16);
                int bY = (cY - (cY % (WIDTH / 16))) / (WIDTH / 16);
                mods.HookFunction("itemClick", new Object[]{bX, bY, (MAPS.map0[bY][bX][1].id != 4) ? 1 : 0, MAPS.map0[bY][bX][(MAPS.map0[bY][bX][1].id != 4) ? 1 : 0].id, PLAYER.getCarriedItem()});
                if (Maths.distanceD((int) PLAYER.posX, (int) PLAYER.posY, bX * WIDTH / 16, bY * WIDTH / 16) <= 1.7 * WIDTH / 16) {
                    PLAYER.getCarriedItem().onClick(MAPS.map0[bY][bX][getHighestBlock(bX, bY)]);
                    if (PLAYER.getCarriedItem().getItemConfiguration().type == Items.Type.BLOCKITEM) {
                        if (setBlock(bX, bY, PLAYER.getCarriedItem().getItemConfiguration().blockId)) {
                            PLAYER.Inventory.get(PLAYER.hotbar[PLAYER.carriedSlot]).count -= 1;
                        }
                    } else if (PLAYER.getCarriedItem().getItemConfiguration().type == Items.Type.INSTRUMENT || PLAYER.getCarriedItem().id == 0) {
                        if (getHighestBlock(bX, bY) != 2 && getHighestBlock(bX, bY) != -1) {
                            /*int z = -1;
                            if (MAPS.map0[bY][bX][0].id != 4) {
                                z = 0;
                            } else if (MAPS.map0[bY][bX][1].id != 4) {
                                z = 1;
                            }*/
                            /*if (z != -1) {
                                int instrType;
                                if (PLAYER.getCarriedItem().id == 0) {
                                    instrType = 3;
                                } else {
                                    instrType = PLAYER.getCarriedItem().getItemConfiguration().instrumentType;
                                }
                                if (PLAYER.hotbar[PLAYER.carriedSlot] != -1)
                                    PLAYER.Inventory.get(PLAYER.hotbar[PLAYER.carriedSlot]).data = PLAYER.Inventory.get(PLAYER.hotbar[PLAYER.carriedSlot]).data + MAPS.map0[bY][bX][z].hit();
                            }*/
                        }
                    }
                }
            }
        }
        if (Gdx.input.isTouched()) {
            isTouched = false;
        }
        int yy = r.nextInt(MAPS.map0.length - 1) + 1;
        int xx = r.nextInt(MAPS.map0[0].length - 1) + 1;
        BLOCKS.getBlockById(MAPS.map0[yy][xx][0].id).randomTick(MAPS.map0[yy][xx][0]);
    }

    public void renderLowLayer() {
        render(false);
    }

    public void renderHighLayer() {
        render(true);
    }

    public void renderLights() {
        BATCH.end();
        if (GAME_INTERFACE != null && !isAnyInterfaceOpen()) {
            float AL = 1.0f - (Maths.module(720 - WorldTime.getTime()) / TL);
            RayHandler.setAmbientLight(AL, AL, AL, 1);
            RayHandler.setCombinedMatrix(GAME_CAMERA.CAMERA.combined);
            RayHandler.updateAndRender();
        }
        BATCH.begin();
    }

    public void renderEntitys() {
        if (entities != null) {
            entities.render(GAME_CAMERA);
        }
    }

    public void render(boolean isHighestLayer) {
        for (int y = MAPS.map0.length - 1; y >= 0; y--) {
            for (int x = 0; x < MAPS.map0[y].length; x++) {
                if (isHighestLayer) {
                    if (PLAYER.currentTileX == x && PLAYER.currentTileY == y) {
                        PLAYER.render(WorldTime);
                        if (Maths.rectCrossing((int) PLAYER.playerBody.x, (int) PLAYER.playerBody.y, (int) PLAYER.playerBody.width, (int) PLAYER.playerBody.height, BLOCK_SIZE * (x - 1), BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE))
                            renderBlock(x - 1, y, true);
                    }
                    renderBlock(x, y, true);
                } else renderBlock(x, y, false);
            }
        }
    }

    public void renderBlock(int x, int y, boolean isHighestLayer) {
        int z = getHighestBlock(x, y);
        if (z != -1)
            if (Maths.rectCrossing(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x * (WIDTH / 16), y * (WIDTH / 16), BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().x * WIDTH / 16, BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().y * WIDTH / 16)) {
                if ((!isHighestLayer) || (isHighestLayer && z == 0)) {
                    if (z == 2 && (GAME_INTERFACE != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(0.7f, 0.7f, 0.7f, 1);
                    }
                    if (!isHighestLayer && z == 0 && BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().isTransparent()) {
                        z = z + 1;
                    }
                    int w = BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().x * BLOCK_SIZE;
                    int h = BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().y * BLOCK_SIZE;
                    BATCH.draw(MAPS.map0[y][x][z].t, x * BLOCK_SIZE, y * BLOCK_SIZE, w, h);

                    if (z == 2 && (GAME_INTERFACE != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(1, 1, 1, 1);
                    }
                }
            }
    }

    /*public Texture getConnectingTexture(Block bl, int uID, int rID, int dID, int lID) {
        if (bl.id == uID && bl.id == dID && bl.id != lID && bl.id != rID) {
            return bl.t0;
        } else if (bl.id != uID && bl.id != dID && bl.id == lID && bl.id == rID) {
            return bl.t1;
        } else if (bl.id != uID && bl.id == dID && bl.id != lID && bl.id == rID) {
            return bl.t2;
        } else if (bl.id != uID && bl.id == dID && bl.id == lID && bl.id != rID) {
            return bl.t3;
        } else if (bl.id == uID && bl.id != dID && bl.id == lID && bl.id != rID) {
            return bl.t4;
        } else if (bl.id == uID && bl.id != dID && bl.id != lID && bl.id == rID) {
            return bl.t5;
        } else if (bl.id == uID && bl.id == dID && bl.id != lID && bl.id == rID) {
            return bl.t6;
        } else if (bl.id != uID && bl.id == dID && bl.id == lID && bl.id == rID) {
            return bl.t7;
        } else if (bl.id == uID && bl.id == dID && bl.id == lID && bl.id != rID) {
            return bl.t8;
        } else if (bl.id == uID && bl.id != dID && bl.id == lID && bl.id == rID) {
            return bl.t9;
        } else if (bl.id == uID && bl.id == dID && bl.id == lID && bl.id == rID) {
            return bl.t10;
        } else if (bl.id != uID && bl.id == dID && bl.id != lID && bl.id != rID) {
            return bl.t11;
        } else if (bl.id != uID && bl.id != dID && bl.id == lID && bl.id != rID) {
            return bl.t12;
        } else if (bl.id == uID && bl.id != dID && bl.id != lID && bl.id != rID) {
            return bl.t13;
        } else if (bl.id != uID && bl.id != dID && bl.id != lID && bl.id == rID) {
            return bl.t14;
        } else if (bl.id != uID && bl.id != dID && bl.id != lID && bl.id != rID) {
            return bl.t15;
        }
        return TEXTURES.undf;
    }*/

    public Maps getMaps() {
        return MAPS;
    }
}
