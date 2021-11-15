package com.kgc.sauw.core.environment.achievements;

import com.badlogic.gdx.Gdx;
import static com.kgc.sauw.core.GameContext.SAUW;

import java.util.ArrayList;

public class Achievements {
    private static final ArrayList<Achievement> achievements = new ArrayList<>();

    public static void checkAchievements(AchievementsData achievementsData) {
        try {
            for (Achievement achievement : achievements)
                if (achievement.check()) {
                    giveAchievement(achievement, achievementsData);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void giveAchievement(String id, AchievementsData achievementsData) {
        for (Achievement achievement : achievements) {
            if (achievement.id == SAUW.getId(id)) giveAchievement(achievement, achievementsData);
        }
    }

    public static void giveAchievement(Achievement achievement, AchievementsData achievementsData) {
        achievementsData.addAchievement(achievement);
    }

    public static void defineAchievement(Achievement achievement) {
        achievements.add(achievement);
        Gdx.app.log("Achievements", "Achievement defined with integer id " + achievement.id);
    }

}
