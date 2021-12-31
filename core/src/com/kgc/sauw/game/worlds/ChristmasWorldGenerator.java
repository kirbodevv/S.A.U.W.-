package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.chunk.Chunk;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

public class ChristmasWorldGenerator implements AbstractWorldGenerator {
    private static long seed = 899308403;

    @Override
    public void setSeed(long seed) {
        ChristmasWorldGenerator.seed = seed;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        return null;
    }
}
