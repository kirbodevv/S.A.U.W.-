package com.kgc.sauw.core.environment;

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
