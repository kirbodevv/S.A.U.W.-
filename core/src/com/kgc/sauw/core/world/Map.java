package com.kgc.sauw.core.world;

import com.kgc.sauw.core.block.Blocks;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.world.chunk.Chunk;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Map {

    public static final int xSize = 40;
    public static final int ySize = 40;
    public static final int zSize = 3;

    public int chunksX, chunksY;
    private final Chunk[][] chunks = new Chunk[16][16];

    public Chunk[][] getChunks() {
        return chunks;
    }

    public Chunk getChunk(int x, int z) {
        return chunks[x][z];
    }

    public void setChunk(Chunk chunk, int x, int z) {
        chunks[x][z] = chunk;
    }

    /*public DataBuffer toDataBuffer() {
        DataBuffer buffer = new DataBuffer();
        int[] worldArray = new int[xSize * ySize * zSize];
        int[] mapDmg = new int[map0.length * map0[0].length * map0[0][0].length];
        ExtraData[] tileEntities;
        int i = 0;
        int tileEntitiesCount = 0;
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    Tile tile = getTile(x, y, z);
                    worldArray[i] = tile.id;
                    mapDmg[i] = tile.damage;
                    if (tile.containers.size() > 0) {
                        tileEntitiesCount++;
                    }
                    i++;
                }
            }
        }
        buffer.put("mapIds", worldArray);
        buffer.put("mapDamage", mapDmg);
        if (tileEntitiesCount > 0) {
            tileEntities = new ExtraData[tileEntitiesCount];
            int j = 0;
            for (Tile[][] tiles : map0) {
                for (Tile[] tiles1 : tiles) {
                    for (Tile tile : tiles1) {
                        if (tile.containers.size() > 0) {
                            tileEntities[j] = tile;
                            j++;
                        }
                    }
                }
            }
            buffer.put("tileEntities", tileEntities);
        }
        buffer.put("tileEnCount", tileEntitiesCount);
        return buffer;
    }*/

    public void update() {
        Blocks.blockTick();
        for (Chunk[] chunks_ : chunks) {
            for (Chunk chunk : chunks_) {
                chunk.update();
            }
        }
    }

    public Tile getTile(int x, int y, int z) {
        return getChunk(x / Chunk.CHUCK_SIZE_X, z / Chunk.CHUCK_SIZE_Z).
                getTile(x % Chunk.CHUCK_SIZE_X, y, z % Chunk.CHUCK_SIZE_Z);
    }

    public int getHighestBlock(int x, int z) {
        for (int y = 0; y < Map.zSize; y++) {
            if (getTile(x, y, z).id != SAUW.getId("block:air")) {
                return y;
            }
        }
        return -1;
    }

    public boolean setBlock(Tile tile) {
        if (getTile(tile.x, tile.y, tile.z) != null && getTile(tile.x, tile.y, tile.z).body != null)
            Physic.destroyBody(getTile(tile.x, tile.y, tile.z).body);
        getChunk(tile.x / Chunk.CHUCK_SIZE_X, tile.z / Chunk.CHUCK_SIZE_Z).setTile(tile);
        tile.setBodyAndLight();
        return true;
    }

    public boolean setBlock(int x, int y, int id) {
        int z;
        if (getTile(x, y, 1).id == SAUW.getId("block:air")) {
            z = 1;
        } else if (getTile(x, y, 0).id == SAUW.getId("block:air")) {
            z = 0;
        } else {
            return false;
        }
        return setBlock(x, y, z, id);
    }

    public boolean setBlock(int x, int y, String id) {
        return setBlock(x, y, SAUW.getId(id));
    }

    public boolean setBlock(int x, int y, int z, int id) {
        Tile tile = TileFactory.createTile(id, x, y, z);
        return setBlock(tile);
    }

    public void setBlock(int x, int y, int z, String id) {
        setBlock(x, y, z, SAUW.getId(id));
    }
}