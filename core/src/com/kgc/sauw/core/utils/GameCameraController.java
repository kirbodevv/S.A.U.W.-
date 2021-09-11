package com.kgc.sauw.core.utils;

import com.kgc.sauw.core.environment.world.Map;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;

public class GameCameraController {
    private static float camX, camY;

    public static void init() {
        setSize();
    }

    public static void setSize() {
        GAME_CAMERA.resize(20);
        setCameraPosition();
        GAME_CAMERA.lookAt(camX, camY, false);
    }

    public static void update() {
        setCameraPosition();
        GAME_CAMERA.lookAt(camX, camY, true);
        GAME_CAMERA.update(BATCH);
    }

    public static void setCameraPosition() {
        camX = (PLAYER.getPosition().x + (PLAYER.getSize().x / 2) - (GAME_CAMERA.W / 2f));
        camY = (PLAYER.getPosition().y + (PLAYER.getSize().y / 2) - (GAME_CAMERA.H / 2f));
        if (camX < 1) camX = 1;
        if (camY < 1) camY = 1;
        if (camX + GAME_CAMERA.W > Map.xSize - 1)
            camX = Map.xSize - 1 - GAME_CAMERA.W;
        if (camY + GAME_CAMERA.H > Map.ySize - 1)
            camY = Map.ySize - 1 - GAME_CAMERA.H;
    }
}
