package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.environment.block.AbstractFurnaceBlock;
import com.kgc.sauw.core.environment.item.InstrumentItem;
import com.kgc.sauw.core.environment.world.Tile;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.utils.Resource;

import static com.kgc.sauw.game.gui.Interfaces.FURNACE_INTERFACE;

public class Furnace extends AbstractFurnaceBlock {
    private final Animator animator = new Animator();

    String[][] recipes = new String[][]{
            {"item:aluminium_can", "item:aluminium_ingot"},
            {"item:iron_ore", "item:iron_ingot"}
    };
    String[][] fuel = new String[][]{
            {"item:log"}
    };

    public Furnace() {
        sprite.setRegion(Resource.getTexture("Blocks/furnace.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setInstrumentType(InstrumentItem.Type.PICKAXE);
        blockConfiguration.setMinLightingRadius(2);
        blockConfiguration.setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
        blockConfiguration.setCollisionsRectangleByPixels(1, 0, 30, 13, 32);

        animator.addAnimationRegion("animation_region:furnace", Resource.getTexture("Blocks/furnace.png"), 4, 1);
        animator.addAnimation("animation:furnace", "animation_region:furnace", 0.2f, 1, 2, 3);

        GUI = FURNACE_INTERFACE;
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
}