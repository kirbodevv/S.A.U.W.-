package com.kgc.sauw.core.world.generator;

import com.kgc.sauw.core.world.chunk.Chunk;

public interface AbstractWorldGenerator {
    void setSeed(long seed);

    Chunk generateChunk(int x, int z);
}
