package com.kgc.sauw.core.render;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.Filter;
import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.world.Map;
import com.kgc.sauw.core.world.World;

import static com.kgc.sauw.core.entity.EntityManager.ENTITY_MANAGER;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.*;

public class WorldRenderer {
    public static RayHandler rayHandler;

    static {
        rayHandler = new RayHandler(Physic.getWorld());
        rayHandler.setAmbientLight(1, 1, 1, 1);
        RayHandler.useDiffuseLight(true);
        Filter filter = new Filter();
        filter.categoryBits = 0x0001;
        Light.setGlobalContactFilter(filter);
    }

    private static void renderLights(World world) {
        BATCH.end();
        float TL = 720 / 0.6f;
        float AmbientLight = 1.0f - (Maths.module(720 - world.worldTime.getTime()) / TL);
        rayHandler.setAmbientLight(AmbientLight, AmbientLight, AmbientLight, 1);
        rayHandler.setCombinedMatrix(GAME_CAMERA.CAMERA);
        rayHandler.updateAndRender();
        BATCH.begin();
    }

    public static void render(World world) {
        render(false, world);
        render(true, world);
        renderEntities();
        renderLights(world);
    }

    private static void renderEntities() {
        if (ENTITY_MANAGER != null) {
            ENTITY_MANAGER.render(GAME_CAMERA);
        }
    }

    private static void render(boolean isHighestLayer, World world) {
        for (int y = Map.ySize - 1; y >= 0; y--) {
            for (int x = 0; x < Map.xSize; x++) {
                if (isHighestLayer) {
                    if (PLAYER.getCurrentTileX() == x && PLAYER.getCurrentTileY() == y) {
                        PLAYER.render();
                        if (Maths.rectCrossing((int) PLAYER.getPosition().x, (int) PLAYER.getPosition().y, (int) PLAYER.getSize().x, (int) PLAYER.getSize().y, BLOCK_SIZE * (x - 1), BLOCK_SIZE * y, BLOCK_SIZE, BLOCK_SIZE))
                            renderBlock(x - 1, y, true, world);
                    }
                    renderBlock(x, y, true, world);
                } else renderBlock(x, y, false, world);
            }
        }
    }

    public static void renderBlock(int x, int y, boolean isHighestLayer, World world) {
        int z = world.map.getHighestBlock(x, y);
        if (z != -1)
            if (Maths.rectCrossing(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x, y, Blocks.getBlockById(world.map.getTile(x, y, z).id).getBlockConfiguration().getSize().x, Blocks.getBlockById(world.map.getTile(x, y, z).id).getBlockConfiguration().getSize().y)) {
                if (!isHighestLayer || z == 0) {
                    BATCH.setColor(0.7f, 0.7f, 0.7f, 1);
                    if (!isHighestLayer && z == 0 && Blocks.getBlockById(world.map.getTile(x, y, z).id).getBlockConfiguration().isTransparent()) {
                        z = z + 1;
                    }
                    world.map.getTile(x, y, z).render();
                    BATCH.setColor(1, 1, 1, 1);
                }
            }
    }
}
