package com.kgc.sauw.map;

import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.entity.Drop;
import com.kgc.sauw.entity.EntityManager;

import java.util.Random;

import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.map.World.WORLD;

public class Maps {

    public static final int xSize = 40;
    public static final int ySize = 40;
    public static final int zSize = 3;

    private final Tile[][][] map0 = new Tile[ySize][xSize][zSize];

    public void generateWorld() {
        Random r = new Random();
        for (int i = 0; i < map0.length; i++) {
            for (int j = 0; j < map0[i].length; j++) {
                WORLD.setBlock(j, i, 1, 1);
                WORLD.setBlock(j, i, 2, 2);
                WORLD.setBlock(j, i, 0, 4);
                if (r.nextInt(75) == 0) {
                    WORLD.setBlock(j, i, 0, 6);
                } else if (r.nextInt(75) == 0) {
                    WORLD.setBlock(j, i, 0, 10);
                } else if (r.nextInt(75) == 0) {
                    WORLD.setBlock(j, i, 0, 9);
                }
                WORLD.setBlock(i, 0, 0, 14);
                WORLD.setBlock(i, map0[i].length - 1, 0, 14);
                WORLD.setBlock(0, j, 0, 14);
                WORLD.setBlock(map0.length - 1, j, 0, 14);
            }
        }
        Random r1 = new Random();

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (r1.nextInt(75) == 0) {
                    Drop entity = (Drop) EntityManager.spawn("entity:drop", x, y);
                    entity.setItem("item:stick", 1);
                }
                if (r1.nextInt(50) == 0) {
                    Drop entity = (Drop) EntityManager.spawn("entity:drop", x, y);
                    entity.setItem("item:stone", 1);
                }
                if (r1.nextInt(100) == 0) {
                    Drop entity = (Drop) EntityManager.spawn("entity:drop", x, y);
                    entity.setItem("item:vegetable_fiber", 1);
                }
            }

        }
    }

    public DataBuffer toDataBuffer() {
        DataBuffer buffer = new DataBuffer();
        int[] worldArray = new int[map0.length * map0[0].length * map0[0][0].length];
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
    }

    public void update() {
        BLOCKS.blockTick();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    getTile(x, y, z).update();
                }
            }
        }
    }

    public Tile getTile(int x, int y, int z) {
        if ((x >= 0 && x < xSize) && (y >= 0 && y < ySize) && (z >= 0 && z < ySize))
            return map0[y][x][z];
        return null;
    }

    public void setTile(Tile tile) {
        map0[tile.y][tile.x][tile.z] = tile;
    }
}
