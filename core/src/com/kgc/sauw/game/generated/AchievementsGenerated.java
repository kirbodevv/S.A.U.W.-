package com.kgc.sauw.game.generated;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.achievements.Achievement;
import com.kgc.sauw.core.achievements.Achievements;
import com.kgc.sauw.core.achievements.JSChecker;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.StringUtils;

public class AchievementsGenerated {
    public static void init(){
        Achievement achievement;
        

		achievement = new Achievement();
		achievement.icon = Resource.getTexture("item/log.png");
		achievement.giveCoins = 5;
		achievement.achievementChecker = new JSChecker(Gdx.files.internal("js/achievements/hide_behind_a_tree.js").readString(), "hide_behind_a_tree");
		Achievements.registry.register(achievement, "sauw", "hide_behind_a_tree");

		achievement = new Achievement();
		achievement.icon = Resource.getTexture("item/stone.png");
		achievement.giveCoins = 10;
		achievement.achievementChecker = new JSChecker(Gdx.files.internal("js/achievements/stone_items.js").readString(), "stone_items");
		Achievements.registry.register(achievement, "sauw", "stone_items");
    }
}
