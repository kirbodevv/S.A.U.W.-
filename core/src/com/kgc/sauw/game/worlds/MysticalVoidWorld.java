package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.entity.Drop;
import com.kgc.sauw.core.entity.EntityManager;
import com.kgc.sauw.core.sound.Music;
import com.kgc.sauw.core.world.Map;
import com.kgc.sauw.core.world.World;

import java.util.Random;

public class MysticalVoidWorld extends World {
    @Override
    protected void init() {
        setWorldName("%Language/MysticalVoidWorld");
    }

    @Override
    protected void tick() {
        if (worldTime.getHours() > 19 || worldTime.getHours() < 7) {
            Music.play("music/Sunset.mp3");
            Music.setLooping(true);
        } else {
            Music.setLooping(false);
        }
    }

    @Override
    public void createNewWorld() {
        Random r = new Random();
        for (int x = 0; x < Map.xSize; x++) {
            for (int y = 0; y < Map.ySize; y++) {
                map.setBlock(x, y, 1, 1);
                map.setBlock(x, y, 2, 2);
                map.setBlock(x, y, 0, 4);
                if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 6);
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 10);
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 9);
                } else if (r.nextInt(75) == 0) {
                    map.getTile(x, y, 1).setExtraData("isFlower", true);
                }
                map.setBlock(x, 0, 0, 14);
                map.setBlock(x, Map.ySize - 1, 0, 14);
                map.setBlock(0, y, 0, 14);
                map.setBlock(Map.xSize - 1, y, 0, 14);
            }
        }
        map.setBlock(15, 5, 0, "block:table");
        map.setBlock(16, 5, 0, "block:tool_wall");
        map.setBlock(17, 5, 0, "block:furnace");
        map.setBlock(18, 5, 0, "block:chest");
        map.setBlock(19, 5, 0, "block:campfire");
        Random r1 = new Random();

        for (int x = 0; x < Map.xSize; x++) {
            for (int y = 0; y < Map.ySize; y++) {
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
}
