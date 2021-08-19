package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.environment.Achievements;
import com.kgc.sauw.core.environment.Crafting;

public final class Environment {
    public static final Crafting CRAFTING;
    public static final Achievements ACHIEVEMENTS;
    static {
        CRAFTING = new Crafting();
        ACHIEVEMENTS = new Achievements();
    }
}
