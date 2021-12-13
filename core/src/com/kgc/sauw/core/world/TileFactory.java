package com.kgc.sauw.core.world;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.world.chunk.Chunk;

public class TileFactory {
    public static Tile createTile(int id, int x, int y, int z) {
        Block block = GameContext.getBlock(id);
        Tile tile = new Tile();
        tile.createTile(x, y, z, block);
        tile.cx = tile.x % Chunk.CHUCK_SIZE_X;
        tile.cz = tile.z % Chunk.CHUCK_SIZE_Z;
        tile.cy = tile.y;
        return tile;
    }

    public static Tile createTile(String package_, String id, int x, int y, int z) {
        return createTile(GameContext.get(package_).getId(id), x, y, z);
    }

    public static Tile createTile(String package_, String id, int x, int y, int z, int cx, int cz) {
        return createTile(GameContext.get(package_).getId(id), cx * Chunk.CHUCK_SIZE_X + x, y, cz * Chunk.CHUCK_SIZE_Z + z);
    }
}
