package com.kgc.sauw.map;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kgc.sauw.WorldLoader;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.environment.Time;
import com.kgc.sauw.environment.blocks.Block;
import com.kgc.sauw.math.Maths;
import com.kgc.sauw.physic.Physic;
import com.kgc.sauw.utils.ID;

import java.util.Random;

import static com.kgc.sauw.entity.EntityManager.ENTITY_MANAGER;
import static com.kgc.sauw.entity.EntityManager.PLAYER;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.gui.Interfaces.HUD;
import static com.kgc.sauw.gui.Interfaces.isAnyInterfaceOpen;

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
        if (MAPS.getTile(tile.x, tile.y, tile.z) != null && MAPS.getTile(tile.x, tile.y, tile.z).body != null)
            Physic.destroyBody(MAPS.getTile(tile.x, tile.y, tile.z).body);
        MAPS.setTile(tile);
        setBodyAndLight(tile, BLOCKS.getBlockById(tile.id));
        return true;
    }

    public boolean setBlock(int x, int y, int id) {
        int z;
        if (MAPS.getTile(x, y, 1).id == 4) {
            z = 1;
        } else if (MAPS.getTile(x, y, 0).id == 4) {
            z = 0;
        } else {
            return false;
        }
        return setBlock(x, y, z, id);
    }

    public boolean setBlock(int x, int y, int z, int id) {
        Block block = BLOCKS.getBlockById(id);
        Tile tile = new Tile();
        tile.createTile(x, y, z, block);

        return setBlock(tile);
    }

    public void setBlock(int x, int y, int z, String id) {
        setBlock(x, y, z, ID.get(id));
    }

    public int getHighestBlock(int x, int y) {
        for (int z = 0; z < Maps.zSize; z++) {
            if (MAPS.getTile(x, y, z).id != 4) {
                return z;
            }
        }
        return -1;
    }

    public void update() {
        PLAYER.update();
        ENTITY_MANAGER.update();
        if (Gdx.input.isTouched()) {
            if (!isTouched) {
                if (HUD.isTouched())
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

                if (Maths.distanceD((int) PLAYER.getPosition().x, (int) PLAYER.getPosition().y, bX, bY) <= 2f) {
                    PLAYER.getCarriedItem().onClick(MAPS.getTile(bX, bY, getHighestBlock(bX, bY)));
                    if (PLAYER.getCarriedItem().getItemConfiguration().type == Items.Type.BLOCK_ITEM) {
                        if (setBlock(bX, bY, PLAYER.getCarriedItem().getItemConfiguration().blockId)) {
                            PLAYER.inventory.containers.get(PLAYER.hotbar[PLAYER.carriedSlot]).count -= 1;
                        }
                    }
                }
            }
        }
        if (Gdx.input.isTouched()) {
            isTouched = false;
        }
        int yy = random.nextInt(Maps.ySize - 1) + 1;
        int xx = random.nextInt(Maps.xSize - 1) + 1;
        BLOCKS.getBlockById(MAPS.getTile(xx, yy, 0).id).randomTick(MAPS.getTile(xx, yy, 0));
    }

    public void renderLowLayer() {
        render(false);
    }

    public void renderHighLayer() {
        render(true);
    }

    public void renderLights() {
        BATCH.end();
        if (HUD != null && !isAnyInterfaceOpen()) {
            float AL = 1.0f - (Maths.module(720 - WorldTime.getTime()) / TL);
            RayHandler.setAmbientLight(AL, AL, AL, 1);
            RayHandler.updateAndRender();
        }
        BATCH.begin();
    }

    public void renderEntities() {
        if (ENTITY_MANAGER != null) {
            ENTITY_MANAGER.render(GAME_CAMERA);
        }
    }

    public void render(boolean isHighestLayer) {
        for (int y = Maps.ySize - 1; y >= 0; y--) {
            for (int x = 0; x < Maps.xSize; x++) {
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
            if (Maths.rectCrossing(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x, y, BLOCKS.getBlockById(MAPS.getTile(x, y, z).id).getBlockConfiguration().getSize().x, BLOCKS.getBlockById(MAPS.getTile(x, y, z).id).getBlockConfiguration().getSize().y)) {
                if (!isHighestLayer || z == 0) {
                    if (z == 2 && (HUD != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(0.7f, 0.7f, 0.7f, 1);
                    }
                    if (!isHighestLayer && z == 0 && BLOCKS.getBlockById(MAPS.getTile(x, y, z).id).getBlockConfiguration().isTransparent()) {
                        z = z + 1;
                    }
                    MAPS.getTile(x, y, z).render();
                    if (z == 2 && (HUD != null && !isAnyInterfaceOpen())) {
                        BATCH.setColor(1, 1, 1, 1);
                    }
                }
            }
    }
}