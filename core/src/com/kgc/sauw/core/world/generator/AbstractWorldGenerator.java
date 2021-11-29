package com.kgc.sauw.core.world.generator;

import com.kgc.sauw.core.world.Map;

public interface AbstractWorldGenerator {
    Map generate(long seed);
}
