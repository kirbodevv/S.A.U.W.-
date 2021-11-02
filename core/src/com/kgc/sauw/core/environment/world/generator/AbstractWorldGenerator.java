package com.kgc.sauw.core.environment.world.generator;

import com.kgc.sauw.core.environment.world.Map;

public interface AbstractWorldGenerator {
    Map generate(long seed);
}
