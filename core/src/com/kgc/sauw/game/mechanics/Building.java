package com.kgc.sauw.game.mechanics;

public class Building {
    public static boolean building = false;

    public static void startBuilding() {
        //GAME_CAMERA.setTargetZoom(1.25f);
        building = true;
    }

    public static void stopBuilding() {
        //GAME_CAMERA.setTargetZoom(1f);
        building = false;
    }
}
