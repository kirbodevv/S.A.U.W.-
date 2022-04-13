package com.kgc.sauw.core;

import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.achievements.Achievement;
import com.kgc.sauw.core.achievements.Achievements;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.item.Items;
import com.kgc.sauw.core.recipes.Recipe;
import com.kgc.sauw.core.recipes.Recipes;
import com.kgc.sauw.core.utils.ID;

import java.util.HashMap;

public class GameContext {
    public static final GameContext SAUW = registeredContext("sauw");
    private static HashMap<String, GameContext> gameContexts;

    public static GameContext get(String package_) {
        return gameContexts.get(package_);
    }

    public static GameContext registeredContext(String package_) {
        if (gameContexts == null) {
            gameContexts = new HashMap<>();
        }
        if (!gameContexts.containsKey(package_)) {
            GameContext context = new GameContext(package_);
            gameContexts.put(package_, context);
            Gdx.app.log("Game context", "Registered game context with namespace \"" + package_ + "\"");
            return context;
        }
        return null;
    }

    private final String package_;

    public String getPackage() {
        return package_;
    }

    public GameContext(String package_) {
        this.package_ = package_;
    }

    public int registerId(String key) {
        return ID.registeredId(package_, key);
    }

    public int getId(String key) {
        return ID.get(package_, key);
    }

    public static Block getBlock(int id) {
        return Blocks.registry.get(id);
    }

    public static Item getItem(int id) {
        return Items.registry.get(id);
    }

    public static Achievement getAchievement(int id) {
        return Achievements.registry.get(id);
    }

    public static Recipe getRecipe(int id) {
        return Recipes.registry.get(id);
    }
}
