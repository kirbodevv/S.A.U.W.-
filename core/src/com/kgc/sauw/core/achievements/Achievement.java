package com.kgc.sauw.core.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.registry.RegistryObject;
import com.kgc.sauw.core.utils.languages.Languages;

public class Achievement extends RegistryObject {
    public String title;
    public String description;
    public Texture icon;
    public int giveCoins;
    public AchievementChecker achievementChecker;

    public boolean check() {
        if (achievementChecker != null) return achievementChecker.check();
        return false;
    }

    @Override
    public void init() {
        title = Languages.getString(package_ + ".achievements." + stringId + ".title");
        description = Languages.getString(package_ + ".achievements." + stringId + ".description");
    }
}
