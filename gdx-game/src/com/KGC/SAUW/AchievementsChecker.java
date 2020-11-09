package com.KGC.SAUW;

public class AchievementsChecker {
    public void update(World World, Player pl, Achievements achievements, GameInterface GI, Settings settings){
		if (World.maps.map0[pl.mY - 1][pl.mX][0].id == 6) {
			achievements.giveAchievment(pl, "SZD", GI, settings);
		}
		if(pl.getCountOfItems(14) > 0 || pl.getCountOfItems(15) > 0 || pl.getCountOfItems(22) > 0){
			achievements.giveAchievment(pl, "stoneItems", GI, settings);
		}
	}
}
