package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.sound.Music;
import com.kgc.sauw.core.world.World;
import com.kgc.sauw.core.world.generator.AbstractWorldGenerator;

public class MysticalVoidWorld extends World {
    private com.badlogic.gdx.audio.Music windSound;

    @Override
    public String getName() {
        return "MysticalVoidWorld";
    }

    @Override
    protected void init() {
        windSound = Resource.getMusic("sound/wind.mp3");
        windSound.setLooping(true);
        windSound.setVolume(0.25f);
    }

    @Override
    protected void tick() {
        windSound.play();
    }

    /*@Override
    public void createNewWorld() {
        Random r = new Random();
        for (int x = 0; x < Map.xSize; x++) {
            for (int y = 0; y < Map.ySize; y++) {
                map.setBlock(x, y, 2, "block:stone");
                map.setBlock(x, y, 1, "block:grass");
                map.setBlock(x, y, 0, "block:air");
                if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 6);
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 10);
                } else if (r.nextInt(75) == 0) {
                    map.setBlock(x, y, 0, 9);
                } else if (r.nextInt(75) == 0) {
                    map.getTile(x, y, 1).setExtraData("isFlower", true);
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
*/
    @Override
    public AbstractWorldGenerator getWorldGenerator() {
        return TestWorldGenerator.INSTANCE;
    }

    @Override
    public int getSkyLight() {
        return 0xFFFFFFFF;
    }
}
