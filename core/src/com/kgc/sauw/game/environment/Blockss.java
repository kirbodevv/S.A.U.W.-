package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.environment.block.Air;
import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.game.blocks.*;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Blockss {
    public Blockss() {
        SAUW.registeredBlock(new Air(), "air");
        SAUW.registeredBlock(new Barrier(), "barrier");

        SAUW.registeredBlock(new Grass(), "grass");
        SAUW.registeredBlock(new Stone(), "stone");
        SAUW.registeredBlock(new Chest(), "chest");
        SAUW.registeredBlock(new Tree(), "tree");
        SAUW.registeredBlock(new Wood(), "wood");
        SAUW.registeredBlock(new StoneLump(), "stone_lump");
        SAUW.registeredBlock(new IronOre(), "iron_ore");
        SAUW.registeredBlock(new Furnace(), "furnace");
        SAUW.registeredBlock(new Dirt(), "dirt");
        SAUW.registeredBlock(new Sapling(), "sapling");
        SAUW.registeredBlock(new Campfire(), "campfire");
        SAUW.registeredBlock(new ChristmasTree(), "christmas_tree");
        SAUW.registeredBlock(new Snow(), "snow");
        SAUW.registeredBlock(new Water(), "water");
        SAUW.registeredBlock(new Table(), "table");
        SAUW.registeredBlock(new ToolWall(), "tool_wall");
        SAUW.registeredBlock(new Workbench(), "workbench");
    }
}