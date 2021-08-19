package com.kgc.sauw.core.block;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Blocks {
    private static final ArrayList<BlocksArray> BLOCKS_ARRAYS = new ArrayList<>();

    private static float stateTime = 0.0f;

    public static void addBlocksArray(BlocksArray blocksArray) {
        BLOCKS_ARRAYS.add(blocksArray);
        Gdx.app.log("BLOCKS", "added blocks array, count of blocks " + blocksArray.blocks.size());
    }

    public static Block getBlockById(int id) {
        for (BlocksArray blocksArray : BLOCKS_ARRAYS) {
            for (Block block : blocksArray.blocks)
                if (block.id == id) return block;
        }
        return null;
    }

    public static void animationTick() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public static void blockTick() {
        for (BlocksArray blocksArray : BLOCKS_ARRAYS)
            for (Block block : blocksArray.blocks)
                block.tick();
    }
}
