package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.environment.block.Air;
import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.game.blocks.*;

import static com.kgc.sauw.core.GameContext.SAUW;

public class GameBlocks {
    public GameBlocks() {
        Blocks.INSTANCE.register(new Air(), "sauw", "air");
        Blocks.INSTANCE.register(new Barrier(), "sauw", "barrier");

        Blocks.INSTANCE.register(new Grass(), "sauw", "grass");
        Blocks.INSTANCE.register(new Stone(), "sauw", "stone");
        Blocks.INSTANCE.register(new Chest(), "sauw", "chest");
        Blocks.INSTANCE.register(new Tree(), "sauw", "tree");
        Blocks.INSTANCE.register(new Wood(), "sauw", "wood");
        Blocks.INSTANCE.register(new StoneLump(), "sauw", "stone_lump");
        Blocks.INSTANCE.register(new IronOre(), "sauw", "iron_ore");
        Blocks.INSTANCE.register(new Furnace(), "sauw", "furnace");
        Blocks.INSTANCE.register(new Dirt(), "sauw", "dirt");
        Blocks.INSTANCE.register(new Sapling(), "sauw", "sapling");
        Blocks.INSTANCE.register(new Campfire(), "sauw", "campfire");
        Blocks.INSTANCE.register(new ChristmasTree(), "sauw", "christmas_tree");
        Blocks.INSTANCE.register(new Snow(), "sauw", "snow");
        Blocks.INSTANCE.register(new Water(), "sauw", "water");
        Blocks.INSTANCE.register(new Table(), "sauw", "table");
        Blocks.INSTANCE.register(new ToolWall(), "sauw", "tool_wall");
        Blocks.INSTANCE.register(new Workbench(), "sauw", "workbench");
    }
}