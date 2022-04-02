package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.world.TileFactory;
import com.kgc.sauw.core.world.chunk.Chunk;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;
import com.kgc.sauw.core.world.generator.WorldGeneratorUtils;
import com.kgc.utils.OpenSimplexNoise;

import java.util.Random;

import static com.kgc.sauw.core.world.chunk.Chunk.*;

public class TestWorldGenerator implements AbstractWorldGenerator {
    public static final TestWorldGenerator INSTANCE = new TestWorldGenerator();
    OpenSimplexNoise openSimplexNoise;
    Random random;

    @Override
    public void setSeed(long seed) {
        openSimplexNoise = new OpenSimplexNoise(seed);
        random = new Random(seed);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        Chunk chunk = new Chunk(x, z);
        Tile[][][] tiles = new Tile[CHUCK_SIZE_X][CHUCK_SIZE_Y][CHUCK_SIZE_Z];

        for (int bx = 0; bx < CHUCK_SIZE_X; bx++) {
            for (int bz = 0; bz < CHUCK_SIZE_Z; bz++) {
                float val = (float) WorldGeneratorUtils.sumOctave(openSimplexNoise, 8, x * CHUCK_SIZE_X + bx, z * CHUCK_SIZE_Z + bz, .1, 0.05f, 0, 1);
                tiles[bx][2][bz] = TileFactory.createTile("sauw", "block:stone", bx, 2, bz, x, z);
                if (val < 0.4f) {
                    tiles[bx][1][bz] = TileFactory.createTile("sauw", "block:ice", bx, 1, bz, x, z);
                } else if (val < 0.55f) {
                    tiles[bx][1][bz] = TileFactory.createTile("sauw", "block:sand", bx, 1, bz, x, z);
                } else {
                    tiles[bx][1][bz] = TileFactory.createTile("sauw", "block:grass", bx, 1, bz, x, z);
                    if (random.nextInt(10) == 0)
                        tiles[bx][0][bz] = TileFactory.createTile("sauw", "block:tree", bx, 0, bz, x, z);
                }
                if (tiles[bx][0][bz] == null)
                    tiles[bx][0][bz] = TileFactory.createTile("sauw", "block:air", bx, 0, bz, x, z);
                tiles[bx][1][bz].rotation = (int) (Math.random() * 3);
            }
        }
        tiles[0][0][0] = TileFactory.createTile("sauw", "block:campfire", 0, 0, 0, x, z);
        chunk.setTiles(tiles);
        return chunk;
    }
}
