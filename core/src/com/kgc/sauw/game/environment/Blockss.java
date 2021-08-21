package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.blocks.*;

public class Blockss {
    public Blockss() {
        Blocks.addBlock(new Air());
        Blocks.createBlock(ID.registeredId("block:barrier", 14), Resource.getTexture("Blocks/undefined.png"));

        Blocks.addBlock(new Grass());
        Blocks.addBlock(new Stone());
        Blocks.addBlock(new Chest());
        Blocks.addBlock(new Tree());
        Blocks.addBlock(new Wood());
        Blocks.addBlock(new StoneLump());
        Blocks.addBlock(new IronOre());
        Blocks.addBlock(new Furnace());
        Blocks.addBlock(new Dirt());
        Blocks.addBlock(new Sapling());
        Blocks.addBlock(new Campfire());
        Blocks.addBlock(new ChristmasTree());
        Blocks.addBlock(new Snow());
        Blocks.addBlock(new Water());
        Blocks.addBlock(new Table());
        Blocks.addBlock(new ToolWall());
        Blocks.addBlock(new Workbench());
    }
}