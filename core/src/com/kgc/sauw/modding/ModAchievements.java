package com.kgc.sauw.modding;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.achievements.Achievement;
import com.kgc.sauw.core.achievements.Achievements;
import com.kgc.sauw.core.achievements.JSChecker;
import com.kgc.sauw.modding.json_data.JSONAchievement;
import org.json.JSONObject;

public class ModAchievements {
    public ModAchievements(FileHandle achievementsDir, ModScripts modScripts, GameContext gameContext) {
        FileHandle[] files = achievementsDir.list();
        for (FileHandle file : files) {
            if (file.name().endsWith(".achievement.json")) {
                JSONAchievement jsonAchievement = new JSONAchievement();
                jsonAchievement.parse(new JSONObject(file.readString()));
                Achievement achievement = jsonAchievement.toObject();
                achievement.achievementChecker = new JSChecker(modScripts.getScript(jsonAchievement.script), jsonAchievement.id);
                Achievements.INSTANCE.register(achievement, gameContext.getPackage(), jsonAchievement.id);
            }
        }
    }
}
