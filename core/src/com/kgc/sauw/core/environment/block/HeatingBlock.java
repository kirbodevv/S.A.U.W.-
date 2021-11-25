package com.kgc.sauw.core.environment.block;

import com.kgc.sauw.core.environment.world.Tile;

public interface HeatingBlock {
    double heatTemperature(Tile tile);
}
