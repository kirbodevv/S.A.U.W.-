package com.kgc.sauw.core.utils;

import com.kgc.sauw.core.environment.world.Map;
import com.kgc.sauw.core.environment.world.World;

public class WorldUtils {
    public static void fillLayer(World world, int layer, String id) {
        for (int x = 0; x < Map.xSize; x++) {
            for (int y = 0; y < Map.ySize; y++) {
                world.map.setBlock(x, y, layer, id);
            }
        }
    }
}
