package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.block.BlocksArray;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.game.blocks.*;

public class Blocks extends BlocksArray {
    public Blocks() {
        addBlock(new Air());
        createBlock(ID.registeredId("block:barrier", 14), Resource.getTexture("Blocks/undefined.png"));

        addBlock(new Grass());
        addBlock(new Stone());
        addBlock(new Chest());
        addBlock(new Tree());
        addBlock(new Wood());
        addBlock(new StoneLump());
        addBlock(new IronOre());
        addBlock(new Furnace());
        addBlock(new Dirt());
        addBlock(new Sapling());
        addBlock(new Campfire());
        addBlock(new ChristmasTree());
        addBlock(new Snow());
        addBlock(new Water());
        addBlock(new Table());
        addBlock(new ToolWall());
        addBlock(new Workbench());
    }
}