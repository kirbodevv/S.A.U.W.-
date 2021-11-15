package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.Color;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.item.InstrumentItem;
import com.kgc.sauw.core.environment.item.Items;
import com.kgc.sauw.core.environment.world.Tile;
import com.kgc.sauw.core.graphic.Animator;
import com.kgc.sauw.core.utils.Resource;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.game.gui.Interfaces.FURNACE_INTERFACE;

public class Furnace extends Block {
    private final Animator animator = new Animator();

    String[][] recipes = new String[][]{
            {"item:aluminium_can", "item:aluminium_ingot"},
            {"item:iron_ore", "item:iron_ingot"}
    };
    String[][] fuel = new String[][]{
            {"item:log"}
    };

    public Furnace() {
        super(SAUW.registeredId("block:furnace", 11), Resource.getTexture("Blocks/furnace.png"));

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
    public void setDefaultExtraData(Tile tile) {
        tile.setExtraData("progress", 0);
        tile.setExtraData("fuel", 0);
        tile.setExtraData("curRecId", 0);
    }

    @Override
    public void onPlace(Tile tile) {
        tile.t = animator.getFrames("animation_region:furnace")[0];
    }

    @Override
    public void tick(Tile tile) {
        if ((int) (tile.getExtraData("fuel")) <= 0) {
            for (int i = 0; i < fuel.length; i++) {
                Container fuelCon = tile.getContainer("FuelSlot");
                for (int j = 0; j < recipes.length; j++) {
                    if (fuelCon.getId() == SAUW.getId(fuel[i][0]) && tile.getContainer("IngSlot").getId() == SAUW.getId(recipes[i][0])) {
                        fuelCon.setItem(fuelCon.getId(), fuelCon.getCount() - 1, fuelCon.getDamage());
                        tile.setExtraData("fuel", 200);
                    }
                }
            }
        } else {
            tile.setExtraData("fuel", (int) tile.getExtraData("fuel") - 1);
            if ((int) (tile.getExtraData("progress")) <= 0) {
                for (String[] recipe : recipes) {
                    Container ingCon = tile.getContainer("IngSlot");
                    if (ingCon.getId() == SAUW.getId(recipe[0]) && (tile.getContainer("ResultSlot").getId() == SAUW.getId(recipe[1]) || tile.getContainer("ResultSlot").getId() == 0) && tile.getContainer("ResultSlot").getCount() < Items.getItemById(SAUW.getId(recipe[1])).getItemConfiguration().maxCount) {
                        tile.setExtraData("progress", 100);
                        tile.setExtraData("curRecId", SAUW.getId(recipe[1]));
                    }
                }
            } else {
                tile.setExtraData("progress", (int) tile.getExtraData("progress") - 1);
                if ((int) tile.getExtraData("progress") <= 0) {
                    Container res = tile.getContainer("ResultSlot");
                    Container ing = tile.getContainer("IngSlot");
                    ing.setItem(ing.getId(), ing.getCount() - 1, ing.getDamage());
                    res.setItem((int) tile.getExtraData("curRecId"), res.getCount() + 1, res.getDamage());
                }
            }
        }
        tile.t = animator.getFrames("animation_region:furnace")[0];
        if ((int) (tile.getExtraData("fuel")) > 0) tile.t = animator.getFrame("animation:furnace");
    }
}