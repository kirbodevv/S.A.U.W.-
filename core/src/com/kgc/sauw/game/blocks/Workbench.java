package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.recipes.Recipe;
import com.kgc.sauw.core.recipes.Recipes;
import com.kgc.sauw.core.recipes.ToolCraftRecipe;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.core.entity.EntityManager.PLAYER;
import static com.kgc.sauw.core.environment.Environment.getWorld;
import static com.kgc.sauw.game.gui.Interfaces.WORKBENCH_INTERFACE;

public class Workbench extends Block {
    public Workbench() {
        super(Resource.getTexture("Blocks/table.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(1, 2, 30, 7, 32);
        GUI = WORKBENCH_INTERFACE;
        ((Slot) GUI.getElement("slot.craft_handsaw_result")).setSF(new Slot.SlotFunctions() {
            @Override
            public boolean isValid(Container container, String fromSlotWithId) {
                return false;
            }

            @Override
            public void onClick() {
                craft(0);
            }

            @Override
            public boolean possibleToDrag() {
                return false;
            }

            @Override
            public void onItemSwapping(Container fromContainer) {

            }
        });
        ((Slot) GUI.getElement("slot.craft_hammer_result")).setSF(new Slot.SlotFunctions() {
            @Override
            public boolean isValid(Container container, String fromSlotWithId) {
                return false;
            }

            @Override
            public void onClick() {
                craft(1);
            }

            @Override
            public boolean possibleToDrag() {
                return false;
            }

            @Override
            public void onItemSwapping(Container fromContainer) {

            }
        });
    }

    @Override
    public void tick(Tile tile) {
        Tile toolWall = findToolWall(tile);
        tile.getContainer("craft_handsaw_result").clear();
        tile.getContainer("craft_hammer_result").clear();
        if (toolWall != null) {
            for (Recipe r : Recipes.INSTANCE.getObjects()) {
                if (r instanceof ToolCraftRecipe) {
                    if (toolWall.getContainer("HandsawSlot").getId() == SAUW.getId("item:handsaw")) {
                        if (tile.getContainer("craft_handsaw_ingredient").getId() == ID.get(r.getIngredients().get(1).fullId)) {
                            tile.getContainer("craft_handsaw_result").setItem(r.getResult().fullId, r.getResult().count * tile.getContainer("craft_handsaw_ingredient").getCount(), 0);
                        }
                    }
                    if (toolWall.getContainer("HammerSlot").getId() == SAUW.getId("item:hammer")) {
                        if (tile.getContainer("craft_hammer_ingredient").getId() == ID.get(r.getIngredients().get(1).fullId)) {
                            tile.getContainer("craft_hammer_result").setItem(r.getResult().fullId, r.getResult().count * tile.getContainer("craft_hammer_ingredient").getCount(), 0);
                        }
                    }
                }
            }
        }
    }

    private void craft(int craftId) {
        Tile tile = getWorld().map.getTile(GUI.currX, GUI.currY, GUI.currZ);
        if (craftId == 0) {
            Container toolWallContainer = findToolWall(tile).getContainer("HandsawSlot");
            Container craftContainer = tile.getContainer("craft_handsaw_ingredient");
            Container craftContainerResult = tile.getContainer("craft_handsaw_result");
            Recipe recipe = getCraft(craftContainerResult.id);

            if (recipe != null) {
                int resultCount = Math.min(craftContainer.count, GameContext.getItem(SAUW.getId("item:handsaw")).getItemConfiguration().maxDamage - toolWallContainer.damage);
                for (int i = 0; i < resultCount; i++) {
                    PLAYER.inventory.addItem(craftContainerResult.id, recipe.getResult().count);
                    toolWallContainer.setItem(toolWallContainer.id, toolWallContainer.count, toolWallContainer.damage + 1);
                    craftContainer.setItem(craftContainer.id, craftContainer.count - 1, 0);
                }
            }
        } else if (craftId == 1) {
            Container toolWallContainer = findToolWall(tile).getContainer("HammerSlot");
            Container craftContainer = tile.getContainer("craft_hammer_ingredient");
            Container craftContainerResult = tile.getContainer("craft_hammer_result");
            Recipe recipe = getCraft(craftContainerResult.id);

            if (recipe != null) {
                int resultCount = Math.min(craftContainer.count, GameContext.getItem(SAUW.getId("item:hammer")).getItemConfiguration().maxDamage - toolWallContainer.damage);
                for (int i = 0; i < resultCount; i++) {
                    PLAYER.inventory.addItem(craftContainerResult.id, recipe.getResult().count);
                    toolWallContainer.setItem(toolWallContainer.id, toolWallContainer.count, toolWallContainer.damage + 1);
                    craftContainer.setItem(craftContainer.id, craftContainer.count - 1, 0);
                }
            }
        }
    }

    private Recipe getCraft(int id) {
        for (Recipe r : Recipes.INSTANCE.getObjects()) {
            if (r instanceof ToolCraftRecipe && ID.get(r.getResult().fullId) == id) return r;
        }
        return null;
    }

    public static Tile findToolWall(Tile tile) {
        Tile toolWall = null;
        if (getWorld().map.getTile(tile.x - 1, tile.y, tile.z).id == SAUW.getId("block:tool_wall")) {
            toolWall = getWorld().map.getTile(tile.x - 1, tile.y, tile.z);
        }
        if (getWorld().map.getTile(tile.x + 1, tile.y, tile.z).id == SAUW.getId("block:tool_wall")) {
            toolWall = getWorld().map.getTile(tile.x + 1, tile.y, tile.z);
        }
        return toolWall;
    }
}