package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.utils.WorldUtils;
import com.kgc.sauw.core.world.Map;
import com.kgc.sauw.core.world.World;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

import java.util.Random;

public class ChristmasWorld extends World {
    @Override
    public String getName() {
        return "ChristmasWorld";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void tick() {

    }

    /*@Override
    public void createNewWorld() {
        Random r = new Random();
        for (int x = 0; x < Map.xSize; x++) {
            for (int y = 0; y < Map.ySize; y++) {
                map.setBlock(x, y, 2, "block:stone");
                map.setBlock(x, y, 1, "block:snow");
                map.setBlock(x, y, 0, "block:air");
                if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, "block:christmas_tree");
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, "block:iron_ore");
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, "block:stone_lump");
                }
            }
        }
        WorldUtils.fill(this, 0, 0, 0, 39, "block:barrier");
        WorldUtils.fill(this, 39, 0, 39, 39, "block:barrier");
        WorldUtils.fill(this, 1, 0, 38, 0, "block:barrier");
        WorldUtils.fill(this, 1, 39, 38, 39, "block:barrier");
        map.setBlock(15, 5, 0, "block:table");
        map.setBlock(16, 5, 0, "block:tool_wall");
        map.setBlock(17, 5, 0, "block:furnace");
        map.setBlock(18, 5, 0, "block:chest");
        map.setBlock(19, 5, 0, "block:campfire");
    }*/

    @Override
    public AbstractWorldGenerator getWorldGenerator() {
        return TestWorldGenerator.INSTANCE;
    }
}
