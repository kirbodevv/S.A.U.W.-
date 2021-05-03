package com.kgc.sauw.utils;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.map.World.WORLD;

public class GameCameraController {
    private static int camX, camY;

    public static void init() {
        GAME_CAMERA.setCurrentCameraZoom(1f);
        setCameraPosition();
        GAME_CAMERA.lookAt(camX, camY, false);
    }

    public static void update() {
        boolean isCameraZooming = false;
        for (int y = PLAYER.getCurrentTileY() - 3; y < PLAYER.getCurrentTileY() + 3; y++) {
            for (int x = PLAYER.getCurrentTileX() - 3; x < PLAYER.getCurrentTileX() + 3; x++) {
                if (y > 0 && y < MAPS.map0.length && x > 0 && x < MAPS.map0[0].length) {
                    if (MAPS.map0[y][x][0].id == 15) {
                        GAME_CAMERA.setCameraZoom(0.75f, 0.015f);
                        isCameraZooming = true;
                    }
                }
            }
        }
        setCameraPosition();
        if (!isCameraZooming) {
            GAME_CAMERA.setCameraZoom(1.25f, 0.015f);
        }
        GAME_CAMERA.lookAt(camX, camY, true);
        GAME_CAMERA.update(BATCH);
    }

    public static void setCameraPosition() {
        camX = (int) ((PLAYER.getPosition().x + (PLAYER.getSize().x / 2)) - (GAME_CAMERA.W / 2));
        camY = (int) (PLAYER.getPosition().y + (PLAYER.getSize().y / 2) - (GAME_CAMERA.H / 2));
        if (camX < BLOCK_SIZE) camX = BLOCK_SIZE;
        if (camY < BLOCK_SIZE) camY = BLOCK_SIZE;
        if (camX + GAME_CAMERA.W > (WORLD.getMaps().map0[0].length - 1) * (SCREEN_WIDTH / 16))
            camX = (int) ((WORLD.getMaps().map0[0].length - 1) * (SCREEN_WIDTH / 16) - GAME_CAMERA.W);
        if (camY + GAME_CAMERA.H > (WORLD.getMaps().map0.length - 1) * (SCREEN_WIDTH / 16))
            camY = (int) ((WORLD.getMaps().map0.length - 1) * (SCREEN_WIDTH / 16) - GAME_CAMERA.H);
    }
}
