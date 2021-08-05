package com.kgc.sauw.core.world;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.entity.Drop;
import com.kgc.sauw.core.entity.EntityManager;
import com.kgc.sauw.core.physic.Physic;
import com.kgc.sauw.core.utils.ID;

import java.util.Random;

import static com.kgc.sauw.game.environment.Environment.BLOCKS;

public class Map {

    public static final int xSize = 40;
    public static final int ySize = 40;
    public static final int zSize = 3;

    private final Tile[][][] map0 = new Tile[ySize][xSize][zSize];

    public void generateWorld() {
        Random r = new Random();
        for (int i = 0; i < map0.length; i++) {
            for (int j = 0; j < map0[i].length; j++) {
                setBlock(j, i, 1, 1);
                setBlock(j, i, 2, 2);
                setBlock(j, i, 0, 4);
                if (r.nextInt(75) == 0) {
                    setBlock(j, i, 0, 6);
                } else if (r.nextInt(75) == 0) {
                    setBlock(j, i, 0, 10);
                } else if (r.nextInt(75) == 0) {
                    setBlock(j, i, 0, 9);
                }
                setBlock(i, 0, 0, 14);
                setBlock(i, map0[i].length - 1, 0, 14);
                setBlock(0, j, 0, 14);
                setBlock(map0.length - 1, j, 0, 14);
            }
            setBlock(15, 5, 0, "block:table");
            setBlock(16, 5, 0, "block:tool_wall");
            setBlock(17, 5, 0, "block:furnace");
            setBlock(18, 5, 0, "block:chest");
            setBlock(19, 5, 0, "block:campfire");
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

    public int getHighestBlock(int x, int y) {
        for (int z = 0; z < Map.zSize; z++) {
            if (getTile(x, y, z).id != 4) {
                return z;
            }
        }
        return -1;
    }

    public boolean setBlock(Tile tile) {
        if (getTile(tile.x, tile.y, tile.z) != null && getTile(tile.x, tile.y, tile.z).body != null)
            Physic.destroyBody(getTile(tile.x, tile.y, tile.z).body);
        map0[tile.y][tile.x][tile.z] = tile;
        tile.setBodyAndLight();
        return true;
    }

    public boolean setBlock(int x, int y, int id) {
        int z;
        if (getTile(x, y, 1).id == 4) {
            z = 1;
        } else if (getTile(x, y, 0).id == 4) {
            z = 0;
        } else {
            return false;
        }
        return setBlock(x, y, z, id);
    }

    public boolean setBlock(int x, int y, int z, int id) {
        Block block = BLOCKS.getBlockById(id);
        Tile tile = new Tile();
        tile.createTile(x, y, z, block);

        return setBlock(tile);
    }

    public void setBlock(int x, int y, int z, String id) {
        setBlock(x, y, z, ID.get(id));
    }
}