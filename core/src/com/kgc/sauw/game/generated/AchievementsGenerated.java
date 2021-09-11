package com.kgc.sauw.game.generated;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.environment.achievements.Achievement;
import com.kgc.sauw.core.environment.achievements.Achievements;
import com.kgc.sauw.core.environment.achievements.JSChecker;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.StringUtils;

public class AchievementsGenerated {
    public static void init(){
        Achievement achievement;
        

		achievement = new Achievement("achievements:hide_behind_a_tree");
		achievement.title = StringUtils.getString("%Language/ach1_title");
		achievement.description = StringUtils.getString("%Language/ach1_txt");
		achievement.icon = Resource.getTexture("Items/log.png");
		achievement.giveCoins = 5;
		achievement.achievementChecker = new JSChecker(Gdx.files.internal("js/achievements/hide_behind_a_tree.js").readString(), "achievements:hide_behind_a_tree");
		Achievements.defineAchievement(achievement);

		achievement = new Achievement("achievements:stone_items");
		achievement.title = StringUtils.getString("%Language/ach2_title");
		achievement.description = StringUtils.getString("%Language/ach2_txt");
		achievement.icon = Resource.getTexture("Items/stone.png");
		achievement.giveCoins = 10;
		achievement.achievementChecker = new JSChecker(Gdx.files.internal("js/achievements/stone_items.js").readString(), "achievements:stone_items");
		Achievements.defineAchievement(achievement);
    }
}
