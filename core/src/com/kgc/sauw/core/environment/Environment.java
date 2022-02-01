package com.kgc.sauw.core.environment;

import com.kgc.sauw.core.entity.entities.player.GameMode;
import com.kgc.sauw.core.world.World;
import com.kgc.sauw.core.world.WorldLoader;

import static com.kgc.sauw.core.entity.entities.player.GameMode.CREATIVE;

public class Environment {
    private static World world;
    private static String saveName;

    public static GameMode gameMode = CREATIVE;

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        Environment.world = world;
    }

    public static void setSaveName(String saveName) {
        Environment.saveName = saveName;
    }

    public static String getSaveName() {
        return saveName;
    }

    public static void save() {
        WorldLoader.save(saveName);
    }

    public static void load() {
        WorldLoader.load(saveName);
    }
}
