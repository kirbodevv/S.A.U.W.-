package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.block.AbstractFurnaceBlock;
import com.kgc.sauw.core.block.LightBlock;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.item.InstrumentItem;
import com.kgc.sauw.core.particle.Particles;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;

import java.util.Random;

import static com.kgc.sauw.game.gui.Interfaces.FURNACE_INTERFACE;

public class Furnace extends AbstractFurnaceBlock implements LightBlock {
    private final Animator animator = new Animator();
    Random random = new Random();

    String[][] recipes = new String[][]{
            {"item:aluminium_can", "item:aluminium_ingot"},
            {"item:iron_ore", "item:iron_ingot"}
    };
    String[][] fuel = new String[][]{
            {"item:log"}
    };

    public Furnace() {
        sprite.setRegion(Resource.getTexture("blocks/furnace.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
        blockConfiguration.setCollisionsRectangleByPixels(1, 0, 30, 13, 32);

        animator.addAnimationRegion("animation_region:furnace", Resource.getTexture("blocks/furnace.png"), 4, 1);
        animator.addAnimation("animation:furnace", "animation_region:furnace", 0.2f, 1, 2, 3);

        GUI = FURNACE_INTERFACE;
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
        if (timer1 >= 40) {
            flameParticles = !flameParticles;
            if (!flameParticles)
                timer1 = 0;
        }
    }

    @Override
    public void tick(Tile tile) {
        super.tick(tile);
        if ((int) (tile.getExtraData("burnTime")) > 0) {
            if (smokeParticles) {
                float x = tile.x + 0.5f;
                float y = tile.y + 1f;
                x += (random.nextFloat() - 0.5) / 4f;
                Particles.addParticle("particle:smoke", x, y, 3);
            }
            if (flameParticles) {
                float x = tile.x + 0.5f;
                float y = tile.y;
                x += (random.nextFloat() - 0.5) / 2f;
                Particles.addParticle("particle:flame", x, y, 3);
            }
        }
    }

    @Override
    public void animationTick(Tile tile) {
        sprite.setRegion((int) (tile.getExtraData("burnTime")) > 0 ?
                animator.getFrame("animation:furnace") :
                animator.getFrames("animation_region:furnace")[0]);
    }

    @Override
    public float getWorkTemperature() {
        return 100;
    }

    @Override
    public int lightLevel() {
        return 2;
    }

    @Override
    public Color lightColor() {
        return new Color(0.8f, 0.6f, 0, 0.5f);
    }
}