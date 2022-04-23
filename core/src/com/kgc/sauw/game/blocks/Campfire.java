package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.block.LightBlock;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.particle.Particles;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;

import java.util.Random;

public class Campfire extends Block implements LightBlock {
    Random random = new Random();
    private final Animator animator;

    public Campfire() {
        super(Resource.getTexture("blocks/campfire.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(8, 2, 18, 10, 32);

        animator = new Animator();
        animator.addAnimationRegion("animation_region:campfire", Resource.getTexture("blocks/campfire_animation.png"), 4, 1);
        animator.addAnimation("animation:campfire", "animation_region:campfire", 0.25f, 0, 1, 2, 3);
    }

    private int timer = 0;
    private int timer1 = 0;
    private boolean smokeParticles = false;
    private boolean flameParticles = false;

    @Override
    public void tick() {
        timer++;
        timer1++;
        if (timer >= 20) {
            smokeParticles = !smokeParticles;
            if (!smokeParticles)
                timer = 0;
        }
        if (timer1 >= 15) {
            flameParticles = !flameParticles;
            if (!flameParticles)
                timer1 = 0;
        }
    }

    @Override
    public void tick(Tile tile) {
        if (smokeParticles) {
            float x = tile.x + 0.5f;
            float y = tile.z + 0.5f;
            x += (random.nextFloat() - 0.5) / 2f;
            Particles.addParticle("particle:smoke", x, y, 3);
        }
        if (flameParticles) {
            float x = tile.x + 0.5f;
            float y = tile.z + 0.5f;
            x += (random.nextFloat() - 0.5) / 2f;
            Particles.addParticle("particle:flame", x, y, 3);
        }
    }

    @Override
    public void animationTick(Tile tile) {
        sprite.setRegion(animator.getFrame("animation:campfire"));
    }

    @Override
    public int lightLevel() {
        return 8;
    }

    @Override
    public Color lightColor() {
        return new Color(0.8f, 0.6f, 0, 0.5f);
    }
}
