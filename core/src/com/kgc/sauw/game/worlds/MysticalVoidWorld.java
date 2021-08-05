package com.kgc.sauw.game.worlds;

import com.kgc.sauw.core.world.World;

public class MysticalVoidWorld extends World {
    @Override
    protected void init() {
        setWorldName("%Language/MysticalVoidWorld");
    }

    @Override
    protected void tick() {

    }

    @Override
    public void createNewWorld() {
        map.generateWorld();
    }
}
