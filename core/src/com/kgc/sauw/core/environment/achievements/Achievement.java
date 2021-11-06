package com.kgc.sauw.core.environment.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.languages.Languages;

public class Achievement {
    public int id;
    public String stringID;
    public String title;
    public String description;
    public Texture icon;
    public int giveCoins;
    public AchievementChecker achievementChecker;

    public Achievement(String id) {
        this.id = ID.registeredId("achievements:" + id);
        this.stringID = id;
        title = Languages.getString("sauw.achievements." + id + ".title");
        description = Languages.getString("sauw.achievements." + id + ".description");
    }

    public boolean check() {
        if (achievementChecker != null) return achievementChecker.check();
        return false;
    }
}
