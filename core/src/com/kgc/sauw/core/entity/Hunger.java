package com.kgc.sauw.core.entity;

public class Hunger {
    public float hunger = 100;

    public void update(Entity entity) {
        if (entity.moving()) {
            hunger -= 0.1f;
        }
    }
}
