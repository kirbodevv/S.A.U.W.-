package com.kgc.sauw.core.block;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class BlocksArray {
    public final ArrayList<Block> blocks = new ArrayList<>();

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void createBlock(int id, Texture t0) {
        addBlock(new Block(id, t0));
    }
}
