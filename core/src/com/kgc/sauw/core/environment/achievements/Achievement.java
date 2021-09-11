package com.kgc.sauw.core.environment.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.utils.ID;

public class Achievement {
    public int id;
    public String stringID;
    public String title;
    public String description;
    public Texture icon;
    public int giveCoins;
    public AchievementChecker achievementChecker;

    public Achievement(String id) {
        this.id = ID.registeredId(id);
        this.stringID = id;
    }

    public boolean check() {
        if (achievementChecker != null) return achievementChecker.check();
        return false;
    }
}
