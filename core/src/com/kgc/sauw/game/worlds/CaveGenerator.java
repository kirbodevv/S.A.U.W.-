package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.TileFactory;
import com.kgc.sauw.core.world.chunk.Chunk;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;
import com.kgc.sauw.core.world.generator.WorldGeneratorUtils;
import com.kgc.utils.OpenSimplexNoise;

import static com.kgc.sauw.core.world.chunk.Chunk.CHUCK_SIZE_X;
import static com.kgc.sauw.core.world.chunk.Chunk.CHUCK_SIZE_Z;

public class CaveGenerator implements AbstractWorldGenerator {
    public static CaveGenerator INSTANCE = new CaveGenerator();
    private OpenSimplexNoise openSimplexNoise;

    @Override
    public void setSeed(long seed) {
        openSimplexNoise = new OpenSimplexNoise(seed);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        Chunk chunk = new Chunk(x, z);

        for (int bx = 0; bx < CHUCK_SIZE_X; bx++) {
            for (int bz = 0; bz < CHUCK_SIZE_Z; bz++) {
                float val = (float) WorldGeneratorUtils.sumOctave(openSimplexNoise, 8, x * CHUCK_SIZE_X + bx, z * CHUCK_SIZE_Z + bz, .1, 0.05f, 0, 1);
                chunk.setTile(TileFactory.createTile("sauw", "block:stone", bx, 2, bz, x, z));
                chunk.setTile(TileFactory.createTile("sauw", "block:stone", bx, 1, bz, x, z));
                if (val < 0.7f) {
                    chunk.setTile(TileFactory.createTile("sauw", "block:stone", bx, 0, bz, x, z));
                }
                if (chunk.getTile(bx, 0, bz) == null)
                    chunk.setTile(TileFactory.createTile("sauw", "block:air", bx, 0, bz, x, z));
            }
        }
        chunk.setTile(TileFactory.createTile("sauw", "block:campfire", 0, 0, 0, x, z));

        return chunk;
    }
}
