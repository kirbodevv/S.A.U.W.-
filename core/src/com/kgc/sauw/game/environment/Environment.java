package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.environment.Achievements;
import com.kgc.sauw.core.environment.Crafting;
import com.kgc.sauw.game.environment.Blocks;
import com.kgc.sauw.game.environment.Items;

public final class Environment {
    public static final Blocks BLOCKS;
    public static final Items ITEMS;
    public static final Crafting CRAFTING;
    public static final Achievements ACHIEVEMENTS;
    static {
        ITEMS = new Items();
        BLOCKS = new Blocks();
        CRAFTING = new Crafting();
        ACHIEVEMENTS = new Achievements();
    }
}
