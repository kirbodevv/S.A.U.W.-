package com.kgc.sauw.core;

import com.kgc.sauw.core.environment.achievements.Achievement;
import com.kgc.sauw.core.environment.achievements.Achievements;
import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.core.environment.item.Item;
import com.kgc.sauw.core.environment.item.Items;
import com.kgc.sauw.core.utils.ID;

import java.util.HashMap;

public class GameContext {
    public static final GameContext SAUW = registeredContext("sauw");
    private static HashMap<String, GameContext> gameContexts;

    public static GameContext get(String package_) {
        return gameContexts.get(package_);
    }

    public static GameContext registeredContext(String package_) {
        GameContext context = new GameContext(package_);
        if (gameContexts == null) {
            gameContexts = new HashMap<>();
        }
        gameContexts.put(package_, context);
        return context;
    }

    public GameContext(String package_) {
        this.package_ = package_;
    }

    private final String package_;

    public int registerId(String key) {
        return ID.registeredId(package_, key);
    }

    public int getId(String key) {
        return ID.get(package_, key);
    }

    public static Block getBlock(int id) {
        return Blocks.INSTANCE.get(id);
    }

    public static Item getItem(int id) {
        return Items.INSTANCE.get(id);
    }

    public static Achievement getAchievement(int id) {
        return Achievements.INSTANCE.get(id);
    }
}
