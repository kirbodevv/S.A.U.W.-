package com.kgc.sauw.core.environment.world;

import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.Filter;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.entity.Entity;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.physic.Physic;

import static com.kgc.sauw.core.entity.EntityManager.ENTITIES_LIST;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;

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
        renderMap(world, false);
        renderMap(world, true);
        renderEntities(world);
        renderLights(world);
    }

    private static void renderEntities(World world) {
        renderEntity(PLAYER, world);
        for (Entity entity : ENTITIES_LIST) renderEntity(entity, world);
    }

    private static void renderEntity(Entity entity, World world) {
        if (Maths.rectCrossing(
                entity.getPosition().x, entity.getPosition().y, entity.getSize().x, entity.getSize().y,
                GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H)) {
            entity.render();
            renderBlock(entity.getCurrentTileX(), entity.getCurrentTileY(), true, world);
        }
    }

    private static void renderMap(World world, boolean highLayer) {
        int camX = (int) Math.floor(GAME_CAMERA.X);
        int camXW = (int) Math.ceil(GAME_CAMERA.X + GAME_CAMERA.W);
        int camY = (int) Math.floor(GAME_CAMERA.Y);
        int camYH = (int) Math.ceil(GAME_CAMERA.Y + GAME_CAMERA.H);
        for (int x = camX; x < camXW; x++) {
            for (int y = camY; y < camYH; y++) {
                renderBlock(x, y, highLayer, world);
            }
        }
    }

    private static void renderBlock(int x, int y, boolean highLayer, World world) {
        int z = world.map.getHighestBlock(x, y);
        if (z != -1)
            if (Maths.rectCrossing(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x, y, GameContext.getBlock(world.map.getTile(x, y, z).id).getBlockConfiguration().getSize().x, GameContext.getBlock(world.map.getTile(x, y, z).id).getBlockConfiguration().getSize().y)) {
                if (!highLayer || z == 0) {
                    BATCH.setColor(0.7f, 0.7f, 0.7f, 1);
                    if (!highLayer && z == 0 && GameContext.getBlock(world.map.getTile(x, y, z).id).getBlockConfiguration().isTransparent()) {
                        z = z + 1;
                    }
                    world.map.getTile(x, y, z).render();
                    BATCH.setColor(1, 1, 1, 1);
                }
            }
    }
}
