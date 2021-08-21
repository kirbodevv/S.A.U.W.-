package com.kgc.sauw.core.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Blocks {
    private static final ArrayList<Block> BLOCKS = new ArrayList<>();

    private static float stateTime = 0.0f;

    public static Block getBlockById(int id) {
        for (Block block : BLOCKS)
            if (block.id == id) return block;
        return null;
    }

    public static void animationTick() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public static void blockTick() {
        for (Block block : BLOCKS)
            block.tick();
    }

    public static void addBlock(Block block) {
        BLOCKS.add(block);
    }

    public static void createBlock(int id, Texture t0) {
        addBlock(new Block(id, t0));
    }
}
