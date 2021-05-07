package com.kgc.sauw.utils;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.WORLD;

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
        if (camX + GAME_CAMERA.W > (WORLD.getMaps().map0[0].length - 1))
            camX = (WORLD.getMaps().map0[0].length - 1) - GAME_CAMERA.W;
        if (camY + GAME_CAMERA.H > (WORLD.getMaps().map0.length - 1))
            camY = (WORLD.getMaps().map0.length - 1) - GAME_CAMERA.H;
    }
}
