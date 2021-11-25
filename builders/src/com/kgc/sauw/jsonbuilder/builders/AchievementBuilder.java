package com.kgc.sauw.jsonbuilder.builders;

import com.kgc.sauw.builder.Builder;
import com.kgc.sauw.builder.FileUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AchievementBuilder implements Builder {
    @Override
    public String build() throws IOException {
        return generateAchievements();
    }

    private static String generateAchievements() throws IOException {
        ArrayList<JSONObject> achievements = FileUtils.loadJsonList("%game%/json/achievements");
        String achievementsTemplate = FileUtils.readFile("%json_builder%/templates/achievements.txt");

        StringBuilder code = new StringBuilder();

        for (JSONObject achievement : achievements) {
            code.append("\n");
            code.append(generateAchievement(achievement));
        }

        return achievementsTemplate
                .replace("$achievements$", code);
    }


    private static String generateAchievement(JSONObject achievement) {
        String code = "";

        String id = achievement.getString("id");
        String icon = achievement.getString("icon");
        String script = achievement.getString("script");
        int giveCoins = achievement.getInt("giveCoins");

        code += "\n\t\tachievement = new Achievement();";
        code += "\n\t\tachievement.icon = Resource.getTexture(\"" + icon + "\");";
        code += "\n\t\tachievement.giveCoins = " + giveCoins + ";";
        code += "\n\t\tachievement.achievementChecker = new JSChecker(Gdx.files.internal(\"js/achievements/" + script + ".js\").readString(), \"" + id + "\");";
        code += "\n\t\tAchievements.INSTANCE.register(achievement, \"sauw\", \"" + id + "\");";

        return code;
    }
}
