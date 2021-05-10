package com.kgc.sauw.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.environment.blocks.*;
import com.kgc.sauw.environment.items.Item;
import com.kgc.sauw.utils.ID;

import java.util.ArrayList;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Blocks {
    public ArrayList<Block> BLOCKS = new ArrayList<Block>();
    public float stateTime = 0.0f;

    public Blocks() {
        createBlock(ID.registeredId("block:air_block", 4), (Texture) null);
        createBlock(ID.registeredId("block:barrier", 14), TEXTURES.undf);

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

    public Item getItemByBlockId(int id) {
        for (Item item : ITEMS.ITEMS) {
            if (item.getItemConfiguration().type == Items.Type.BLOCK_ITEM && item.getItemConfiguration().blockId == id) {
                return item;
            }
        }
        return null;
    }

    public void createBlock(int id, Texture t0) {
        BLOCKS.add(new Block(id, t0));
    }

    public void addBlock(Block block) {
        BLOCKS.add(block);
    }

    public void animationTick() {
        for (Block b : BLOCKS) stateTime += Gdx.graphics.getDeltaTime();
    }

    public void blockTick() {
        for (Block b : BLOCKS) b.tick();
    }

    public Block getBlockById(int id) {
        for (Block block : BLOCKS) {
            if (block.id == id) return block;
        }
        return null;
    }
}