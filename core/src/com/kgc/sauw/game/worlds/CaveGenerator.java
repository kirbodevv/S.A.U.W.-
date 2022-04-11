package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.chunk.Chunk;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

public class CaveGenerator implements AbstractWorldGenerator {
    public static CaveGenerator INSTANCE = new CaveGenerator();

    @Override
    public void setSeed(long seed) {

    }

    @Override
    public Chunk generateChunk(int x, int z) {
        return null;
    }
}
