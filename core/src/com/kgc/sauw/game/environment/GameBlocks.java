package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.block.Air;
import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.game.blocks.*;

public class GameBlocks {
    public GameBlocks() {
        Blocks.registry.register(new Air(), "sauw", "air");
        Blocks.registry.register(new Barrier(), "sauw", "barrier");

        Blocks.registry.register(new Grass(), "sauw", "grass");
        Blocks.registry.register(new Stone(), "sauw", "stone");
        Blocks.registry.register(new Chest(), "sauw", "chest");
        Blocks.registry.register(new Tree(), "sauw", "tree");
        Blocks.registry.register(new Wood(), "sauw", "wood");
        Blocks.registry.register(new StoneLump(), "sauw", "stone_lump");
        Blocks.registry.register(new IronOre(), "sauw", "iron_ore");
        Blocks.registry.register(new Furnace(), "sauw", "furnace");
        Blocks.registry.register(new Dirt(), "sauw", "dirt");
        Blocks.registry.register(new Sapling(), "sauw", "sapling");
        Blocks.registry.register(new Campfire(), "sauw", "campfire");
        Blocks.registry.register(new ChristmasTree(), "sauw", "christmas_tree");
        Blocks.registry.register(new Snow(), "sauw", "snow");
        Blocks.registry.register(new Water(), "sauw", "water");
        Blocks.registry.register(new Table(), "sauw", "table");
        Blocks.registry.register(new ToolWall(), "sauw", "tool_wall");
        Blocks.registry.register(new Workbench(), "sauw", "workbench");
        Blocks.registry.register(new Sand());
        Blocks.registry.register(new Ice());
    }
}