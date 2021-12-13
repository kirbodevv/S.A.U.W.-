package com.kgc.sauw.core.world.chunk;

import com.kgc.sauw.core.world.Tile;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Chunk {
    public static final int CHUCK_SIZE_X = 8;
    public static final int CHUCK_SIZE_Y = 3;
    public static final int CHUCK_SIZE_Z = 8;

    private Tile[][][] tiles = new Tile[CHUCK_SIZE_X][CHUCK_SIZE_Y][CHUCK_SIZE_Z];
    private int x, z;
    private boolean isChanged = false;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void setTile(Tile tile) {
        this.tiles[tile.cx][tile.cz][tile.cy] = tile;
        isChanged = true;
    }

    public Tile getTile(int x, int y, int z) {
        return tiles[x][y][z];
    }

    public void setTiles(Tile[][][] tiles) {
        this.tiles = tiles;
    }

    public byte[] getBytes() {
        return new byte[0];
    }

    public int getHighestBlock(int x, int z) {
        for (int y = CHUCK_SIZE_Y - 1; y >= 0; y--) {
            if (tiles[x][z][y].id != SAUW.getId("block:air")) {
                return y;
            }
        }
        return -1;
    }

    public void update() {
        for (int x = 0; x < CHUCK_SIZE_X; x++) {
            for (int z = 0; z < CHUCK_SIZE_Z; z++) {
                for (int y = 0; y < CHUCK_SIZE_Y; y++) {
                    tiles[x][y][z].update();
                }
            }
        }
    }
}