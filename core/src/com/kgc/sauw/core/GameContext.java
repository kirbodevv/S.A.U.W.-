package com.kgc.sauw.core;

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

    public void registeredBlock(Block block, String id) {
        block.id = ID.registeredId(package_, "block:" + id);
        Blocks.defineBlock(block);
    }

    public int registeredId(String key) {
        return ID.registeredId(package_, key);
    }

    public int registeredId(String key, int id) {
        return ID.registeredId(package_, key, id);
    }

    public int getId(String key) {
        return ID.get(package_, key);
    }

    public Block getBlock(String id) {
        return Blocks.getBlockById(ID.get(package_, id));
    }

    public Item getItem(String id) {
        return Items.getItemById(ID.get(package_, id));
    }
}
