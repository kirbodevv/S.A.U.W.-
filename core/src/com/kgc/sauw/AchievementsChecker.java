package com.kgc.sauw;

import static com.kgc.sauw.ui.interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.ACHIEVEMENTS;
import static com.kgc.sauw.config.Settings.SETTINGS;
import static com.kgc.sauw.map.World.WORLD;

public class AchievementsChecker {
    public static void update() {
        if (WORLD.getMaps().map0[PLAYER.getCurrentTileY() - 1][PLAYER.getCurrentTileX()][0].id == 6) {
            ACHIEVEMENTS.giveAchievment(PLAYER, "SZD", GAME_INTERFACE, SETTINGS);
        }
        if (PLAYER.Inventory.getCountOfItems(14) > 0 || PLAYER.Inventory.getCountOfItems(15) > 0 || PLAYER.Inventory.getCountOfItems(22) > 0) {
            ACHIEVEMENTS.giveAchievment(PLAYER, "stoneItems", GAME_INTERFACE, SETTINGS);
        }
    }
}
