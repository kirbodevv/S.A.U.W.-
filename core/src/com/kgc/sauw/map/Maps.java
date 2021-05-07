package com.kgc.sauw.map;

import com.badlogic.gdx.Gdx;
import com.intbyte.bdb.DataBuffer;
import com.intbyte.bdb.ExtraData;
import com.kgc.sauw.entity.ItemEntityL;
import com.kgc.sauw.utils.ID;

import java.util.Random;

import static com.kgc.sauw.entity.Entities.ENTITIES;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.map.World.WORLD;

public class Maps {
    public Tile[][][] map0 = new Tile[40][40][3];

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
        int WIDTH = Gdx.graphics.getWidth();
        for (int i = 0; i < map0.length; i++) {
            for (int j = 0; j < map0[i].length; j++) {
                if (r1.nextInt(75) == 0) {
                    ENTITIES.spawn(new ItemEntityL(j, i, ID.get("item:stick"), 1, 0));
                }
                if (r1.nextInt(50) == 0) {
                    ENTITIES.spawn(new ItemEntityL(j, i, ID.get("item:stone"), 1, 0));
                }
                if (r1.nextInt(100) == 0) {
                    ENTITIES.spawn(new ItemEntityL(j, i, ID.get("item:vegetable_fiber"), 1, 0));
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
        for (Tile[][] tiles : map0) {
            for (Tile[] tiles1 : tiles) {
                for (Tile tile : tiles1) {
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
            buffer.put("tileEntitys", tileEntities);
        }
        buffer.put("tileEnCount", tileEntitiesCount);
        return buffer;
    }

    public void update() {
        BLOCKS.blockTick();
        for (Tile[][] tiles : map0) {
            for (Tile[] tiles1 : tiles) {
                for (Tile tile : tiles1) {
                    tile.update();
                }
            }
        }
    }
}
