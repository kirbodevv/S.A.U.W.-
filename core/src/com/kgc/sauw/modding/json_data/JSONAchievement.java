package com.kgc.sauw.modding.json_data;

import com.kgc.sauw.core.achievements.Achievement;
import com.kgc.sauw.core.achievements.JSChecker;
import com.kgc.sauw.core.resource.Resource;
import org.json.JSONObject;

public class JSONAchievement implements JSONData<Achievement> {
    public String id;
    public String icon;
    public int giveCoins;
    public String script;

    @Override
    public void parse(JSONObject json) {
        id = json.getString("id");
        icon = json.getString("icon");
        giveCoins = json.getInt("give_coins");
        script = json.getString("script");
    }

    @Override
    public Achievement toObject() {
        Achievement achievement = new Achievement();
        achievement.icon = Resource.getTexture(icon);
        achievement.giveCoins = giveCoins;
        return achievement;
    }
}
