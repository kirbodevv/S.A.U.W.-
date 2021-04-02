package com.kgc.sauw;

import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.config.Settings;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.map.World;

public class AchievementsChecker {
    public void update(World World, Player pl, Achievements achievements, GameInterface GI, Settings settings){
		if (World.maps.map0[pl.currentTileY - 1][pl.currentTileX][0].id == 6) {
			achievements.giveAchievment(pl, "SZD", GI, settings);
		}
		if(pl.getCountOfItems(14) > 0 || pl.getCountOfItems(15) > 0 || pl.getCountOfItems(22) > 0){
			achievements.giveAchievment(pl, "stoneItems", GI, settings);
		}
	}
}
