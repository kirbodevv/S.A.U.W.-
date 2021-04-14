package com.kgc.sauw;

import static com.kgc.sauw.UI.Interfaces.Interfaces.GAME_INTERFACE;
import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.ACHIEVEMENTS;
import static com.kgc.sauw.environment.Environment.SETTINGS;
import static com.kgc.sauw.map.World.WORLD;

public class AchievementsChecker {
    public void update(){
		if (WORLD.getMaps().map0[PLAYER.currentTileY - 1][PLAYER.currentTileX][0].id == 6) {
			ACHIEVEMENTS.giveAchievment(PLAYER, "SZD", GAME_INTERFACE, SETTINGS);
		}
		if(PLAYER.getCountOfItems(14) > 0 || PLAYER.getCountOfItems(15) > 0 || PLAYER.getCountOfItems(22) > 0){
			ACHIEVEMENTS.giveAchievment(PLAYER, "stoneItems", GAME_INTERFACE, SETTINGS);
		}
	}
}
