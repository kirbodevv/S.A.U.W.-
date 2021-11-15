package com.kgc.sauw.core.entity;

public abstract class LivingEntity extends Entity {
    public float maxHealth = 100;
    public float health = 100;

    @Override
    public void spawn(int x, int y) {
        super.spawn(x, y);
        health = maxHealth;
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) kill();
    }

    public void kill() {
        isDead = true;
    }

    public abstract void onDead();
}
