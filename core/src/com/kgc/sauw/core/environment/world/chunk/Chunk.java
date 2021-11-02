package com.kgc.sauw.core.environment.world.chunk;

import com.kgc.sauw.core.environment.world.Tile;

public class Chunk {
    public static final int CHUCK_SIZE_X = 8;
    public static final int CHUCK_SIZE_Y = 3;
    public static final int CHUCK_SIZE_Z = 8;

    private Tile[][][] tiles = new Tile[CHUCK_SIZE_X][CHUCK_SIZE_Z][CHUCK_SIZE_Y];
    private int x, z;
    private boolean isChanged = false;

    public boolean isChanged() {
        return isChanged;
    }

    public void setPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void setTile(Tile tile) {
        this.tiles[tile.x][tile.z][tile.y] = tile;
        isChanged = true;
    }

    public void setTiles(Tile[][][] tiles) {
        this.tiles = tiles;
    }

    public byte[] getBytes() {
        return new byte[0];
    }

    public int getHighestBlock(int x, int z) {
        for (int y = CHUCK_SIZE_Y - 1; y >= 0; y--) {
            if (tiles[x][z][y].id != 4) {
                return y;
            }
        }
        return -1;
    }

    public void readBytes(byte[] bytes) {

    }
}