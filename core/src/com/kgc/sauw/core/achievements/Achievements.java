package com.kgc.sauw.core.achievements;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.registry.Registry;

public class Achievements {

    public static final Registry<Achievement> registry = new Registry<>("achievement");

    public static void checkAchievements(AchievementsData achievementsData) {
        try {
            for (Achievement achievement : registry.getObjects())
                if (achievement.check()) {
                    giveAchievement(achievement, achievementsData);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void giveAchievement(String package_, String id, AchievementsData achievementsData) {
        giveAchievement(registry.get(GameContext.get(package_).getId(id)), achievementsData);
    }

    public static void giveAchievement(Achievement achievement, AchievementsData achievementsData) {
        achievementsData.addAchievement(achievement);
    }
}
