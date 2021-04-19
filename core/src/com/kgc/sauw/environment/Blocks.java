package com.kgc.sauw.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.environment.blocks.*;
import com.kgc.sauw.environment.items.Item;

import java.util.ArrayList;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Blocks {
    public ArrayList<Block> BLOCKS = new ArrayList<Block>();
    public float stateTime = 0.0f;

    public Blocks() {
        createBlock(4, (Texture) null);
        createBlock(14, TEXTURES.undf);

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
    }

    public Item getItemByBlockId(int id) {
        for (Item item : ITEMS.ITEMS) {
            if (item.getItemConfiguration().type == Items.Type.BLOCKITEM && item.getItemConfiguration().blockId == id) {
                return item;
            }
        }
        return null;
    }

    public void createBlock(int id, Texture t0) {
        BLOCKS.add(new Block(id, t0));
    }

    /*public void createBlock(int id, String t0) {
        Texture tt0 = new Texture(Gdx.files.external("S.A.U.W./Mods/" + t0));
        this.createBlock(id, tt0);
        ITEMS.createItem(ITEMS.ITEMS.size(), 1f, "", tt0, 1, id, 64, 0);
    }
*/
    public void addBlock(Block block) {
        BLOCKS.add(block);
    }

    public void animationTick() {
        for (Block b : BLOCKS) stateTime += Gdx.graphics.getDeltaTime();
    }

    public Block getBlockById(int id) {
        for (int i = 0; i < BLOCKS.size(); i++) {
            if (BLOCKS.get(i).id == id) return BLOCKS.get(i);
        }
        return null;
    }
}