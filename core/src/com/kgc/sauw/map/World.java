package com.kgc.sauw.map;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.physics.box2d.*;
import com.kgc.sauw.WorldLoader;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.Time;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.modding.Mods;
import com.kgc.sauw.physic.Physic;
import com.kgc.sauw.utils.PixmapUtils;

import java.util.Random;

import static com.kgc.sauw.entity.Entities.ENTITIES;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.gui.interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.gui.interfaces.Interfaces.isAnyInterfaceOpen;

public class World {
    public static final Maps MAPS;
    public static final World WORLD;

    static {
        MAPS = new Maps();
        WORLD = new World();
    }

    private boolean interfaceTouched;
    private boolean isTouched;
    private final Random random = new Random();
    private boolean worldTouched;
    public Time WorldTime;


    public RayHandler RayHandler;

    String WorldName = null;
    float TL = 720 / 0.6f;

    public void save() {
        if (WorldName != null) {
            WorldLoader.save(WorldName);
        }
    }

    public void setWorldName(String worldName) {
        WorldName = worldName;
    }

    public void createNewWorld() {
        MAPS.generateWorld();
    }

    public void screenshot(String screenshotName) {
        Pixmap pixmap = new Pixmap(32 * 40, 32 * 40, Pixmap.Format.RGBA8888);
        for (int y = MAPS.map0.length - 1; y >= 0; y--) {
            for (int x = 0; x < MAPS.map0[y].length; x++) {
                for (int z = MAPS.map0[y][x].length - 3; z >= 0; z--) {
                    Tile t = MAPS.map0[y][x][z];
                    Block b = BLOCKS.getBlockById(t.id);
                    if (t.t != null) {
                        Pixmap p = PixmapUtils.extractPixmapFromTextureRegion(t.t);
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

    public World() {
        createWorld();
    }

    public void createWorld() {
        this.WorldTime = new Time();
        RayHandler = new RayHandler(Physic.getWorld());
        RayHandler.setAmbientLight(1, 1, 1, 1);
        box2dLight.RayHandler.useDiffuseLight(true);
    }


    public void setBodyAndLight(Tile tile, Block block) {
        if (block.id != 4 && tile.z == 0)
            tile.setBody(Physic.createRectangleBody(tile.block.x, tile.block.y, tile.block.width, tile.block.height, BodyDef.BodyType.StaticBody));
        if (tile.z == 0) tile.setLight(RayHandler, block);
    }

    public boolean setBlock(Tile tile) {
        if (MAPS.map0[tile.y][tile.x][tile.z] != null && MAPS.map0[tile.y][tile.x][tile.z].body != null)
            Physic.destroyBody(MAPS.map0[tile.y][tile.x][tile.z].body);
        MAPS.map0[tile.y][tile.x][tile.z] = tile;
        setBodyAndLight(tile, BLOCKS.getBlockById(tile.id));
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
        return setBlock(x, y, z, id);
    }

    public boolean setBlock(int x, int y, int z, int id) {
        Block block = BLOCKS.getBlockById(id);
        if (x >= 0 && x < MAPS.map0[0].length + 1 && y >= 0 && y < MAPS.map0.length + 1) {
            Tile tile = new Tile();
            tile.createTile(x, y, z, block);

            return setBlock(tile);
        }
        return false;
    }

    public int getHighestBlock(int x, int y) {
        for (int z = 0; z < MAPS.map0[y][x].length; z++) {
            if (MAPS.map0[y][x][z].id != 4) {
                return z;
            }
        }
        return -1;
    }

    public void update(Mods mods) {
        PLAYER.update();
        ENTITIES.update();
        MAPS.update();
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
                float sc = SCREEN_WIDTH / GAME_CAMERA.W;
                int bX = (int) Math.ceil(Gdx.input.getX() / sc + GAME_CAMERA.X) - 1;
                int bY = (int) Math.ceil((Gdx.graphics.getHeight() - Gdx.input.getY()) / sc + GAME_CAMERA.Y) - 1;
                mods.HookFunction("itemClick", new Object[]{bX, bY, (MAPS.map0[bY][bX][1].id != 4) ? 1 : 0, MAPS.map0[bY][bX][(MAPS.map0[bY][bX][1].id != 4) ? 1 : 0].id, PLAYER.getCarriedItem()});
                System.out.println(bX);
                System.out.println(bY);
                if (Maths.distanceD((int) PLAYER.getPosition().x, (int) PLAYER.getPosition().y, bX, bY) <= 2f) {
                    PLAYER.getCarriedItem().onClick(MAPS.map0[bY][bX][getHighestBlock(bX, bY)]);
                    if (PLAYER.getCarriedItem().getItemConfiguration().type == Items.Type.BLOCKITEM) {
                        if (setBlock(bX, bY, PLAYER.getCarriedItem().getItemConfiguration().blockId)) {
                            PLAYER.Inventory.containers.get(PLAYER.hotbar[PLAYER.carriedSlot]).count -= 1;
                        }
                    }
                }
            }
        }
        if (Gdx.input.isTouched()) {
            isTouched = false;
        }
        int yy = random.nextInt(MAPS.map0.length - 1) + 1;
        int xx = random.nextInt(MAPS.map0[0].length - 1) + 1;
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

    public void renderEntities() {
        if (ENTITIES != null) {
            ENTITIES.render(GAME_CAMERA);
        }
    }

    public void render(boolean isHighestLayer) {
        for (int y = MAPS.map0.length - 1; y >= 0; y--) {
            for (int x = 0; x < MAPS.map0[y].length; x++) {
                if (isHighestLayer) {
                    if (PLAYER.getCurrentTileX() == x && PLAYER.getCurrentTileY() == y) {
                        PLAYER.render();
                        if (Maths.rectCrossing((int) PLAYER.getPosition().x, (int) PLAYER.getPosition().y, (int) PLAYER.getSize().x, (int) PLAYER.getSize().y, BLOCK_SIZE * (x - 1), BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE))
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
            if (Maths.rectCrossing(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x, y, BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().x, BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().y)) {
                if (!isHighestLayer || z == 0) {
                    if (z == 2 && (GAME_INTERFACE != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(0.7f, 0.7f, 0.7f, 1);
                    }
                    if (!isHighestLayer && z == 0 && BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().isTransparent()) {
                        z = z + 1;
                    }
                    float w = BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().x;
                    float h = BLOCKS.getBlockById(MAPS.map0[y][x][z].id).getBlockConfiguration().getSize().y;
                    BATCH.draw(MAPS.map0[y][x][z].t, x, y, w, h);

                    if (z == 2 && (GAME_INTERFACE != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(1, 1, 1, 1);
                    }
                }
            }
    }

    public Maps getMaps() {
        return MAPS;
    }
}