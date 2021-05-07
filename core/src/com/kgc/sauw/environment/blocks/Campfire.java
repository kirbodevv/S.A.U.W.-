package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.particle.Particles;

import java.util.Random;

import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Campfire extends Block {
    Random random = new Random();
    private Animator animator;

    public Campfire() {
        super(15, TEXTURES.campfire);

        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setLightingRadius(4);
        BlockConfiguration.setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
        BlockConfiguration.setCollisionsRectangleByPixels(8, 2, 18, 10, 32);

        animator = new Animator();
        animator.addAnimationRegion("animation_region:campfire", TEXTURES.campfire_animation, 4, 1);
        animator.addAnimation("animation:campfire", "animation_region:campfire", 0.25f, 0, 1, 2, 3);
    }

    private float timer = 0f;
    private boolean smokeParticles = false;

    @Override
    public void tick() {
        timer += Gdx.graphics.getRawDeltaTime();
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
