package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.particle.Particles;
import com.kgc.sauw.utils.ID;

import java.util.Random;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Campfire extends Block {
    Random random = new Random();
    private final Animator animator;

    public Campfire() {
        super(ID.registeredId("block:campfire", 15), TEXTURES.campfire);

        blockConfiguration.setTransparent(true);
        blockConfiguration.setLightingRadius(4);
        blockConfiguration.setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
        blockConfiguration.setCollisionsRectangleByPixels(8, 2, 18, 10, 32);

        animator = new Animator();
        animator.addAnimationRegion("animation_region:campfire", TEXTURES.campfire_animation, 4, 1);
        animator.addAnimation("animation:campfire", "animation_region:campfire", 0.25f, 0, 1, 2, 3);
    }

    private float timer = 0f;
    private boolean smokeParticles = false;

    @Override
    public void tick() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= 1) {
            if (!smokeParticles) {
                smokeParticles = true;
            } else {
                smokeParticles = false;
                timer = 0;
            }
        }
    }

    @Override
    public void tick(Tile tile) {
        if (smokeParticles) {
            float x = tile.x + 0.5f;
            float y = tile.y + 0.5f;
            x += (random.nextFloat() - 0.5) / 2f;
            Particles.addParticle("particle:smoke", x, y, 3);
        }
        tile.t = animator.getFrame("animation:campfire");
    }
}
