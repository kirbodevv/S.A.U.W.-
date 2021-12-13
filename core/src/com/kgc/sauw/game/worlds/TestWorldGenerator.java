package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.world.TileFactory;
import com.kgc.sauw.core.world.chunk.Chunk;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

import static com.kgc.sauw.core.world.chunk.Chunk.*;

public class TestWorldGenerator implements AbstractWorldGenerator {
    public static final TestWorldGenerator INSTANCE = new TestWorldGenerator();

    @Override
    public void setSeed(long seed) {
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        Chunk chunk = new Chunk(x, z);
        Tile[][][] tiles = new Tile[CHUCK_SIZE_X][CHUCK_SIZE_Y][CHUCK_SIZE_Z];
        for (int bx = 0; bx < CHUCK_SIZE_X; bx++) {
            for (int bz = 0; bz < CHUCK_SIZE_Z; bz++) {
                tiles[bx][2][bz] = TileFactory.createTile("sauw", "block:stone", bx, 2, bz, x, z);
                tiles[bx][1][bz] = TileFactory.createTile("sauw", "block:snow", bx, 1, bz, x, z);
                tiles[bx][0][bz] = TileFactory.createTile("sauw", "block:air", bx, 0, bz, x, z);
                tiles[bx][1][bz].rotation = (int) (Math.random() * 3);
            }
        }
        chunk.setTiles(tiles);
        return chunk;
    }
}
