package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.environment.block.Air;
import com.kgc.sauw.core.environment.block.Blocks;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.game.blocks.*;

public class Blockss {
    public Blockss() {
        Blocks.defineBlock(new Air());
        Blocks.createBlock(SAUW.registeredId("block:barrier", 14), Resource.getTexture("Blocks/undefined.png"));

        Blocks.defineBlock(new Grass());
        Blocks.defineBlock(new Stone());
        Blocks.defineBlock(new Chest());
        Blocks.defineBlock(new Tree());
        Blocks.defineBlock(new Wood());
        Blocks.defineBlock(new StoneLump());
        Blocks.defineBlock(new IronOre());
        Blocks.defineBlock(new Furnace());
        Blocks.defineBlock(new Dirt());
        Blocks.defineBlock(new Sapling());
        Blocks.defineBlock(new Campfire());
        Blocks.defineBlock(new ChristmasTree());
        Blocks.defineBlock(new Snow());
        Blocks.defineBlock(new Water());
        Blocks.defineBlock(new Table());
        Blocks.defineBlock(new ToolWall());
        Blocks.defineBlock(new Workbench());
    }
}