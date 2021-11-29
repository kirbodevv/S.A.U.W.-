package com.kgc.sauw.core.achievements;

import com.kgc.sauw.core.register.Registry;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Achievements extends Registry<Achievement> {
    public static final Achievements INSTANCE = new Achievements();

    public static void checkAchievements(AchievementsData achievementsData) {
        try {
            for (Achievement achievement : INSTANCE.objects)
                if (achievement.check()) {
                    giveAchievement(achievement, achievementsData);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void giveAchievement(String id, AchievementsData achievementsData) {
        for (Achievement achievement : INSTANCE.objects) {
            if (achievement.getId() == SAUW.getId(id)) giveAchievement(achievement, achievementsData);
        }
    }

    public static void giveAchievement(Achievement achievement, AchievementsData achievementsData) {
        achievementsData.addAchievement(achievement);
    }

    @Override
    public String getIDGroup() {
        return "achievement";
    }
}
