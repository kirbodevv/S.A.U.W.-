package com.kgc.sauw.environment.blocks;

import com.kgc.sauw.environment.Crafting;
import com.kgc.sauw.gui.elements.Slot;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.utils.ID;

import java.util.ArrayList;

import static com.kgc.sauw.entity.EntityManager.PLAYER;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.gui.Interfaces.WORKBENCH_INTERFACE;
import static com.kgc.sauw.map.World.MAPS;

public class Workbench extends Block {
    private final ArrayList<Crafting.craft> crafts;

    public Workbench() {
        super(ID.registeredId("block:workbench"), TEXTURES.table);

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(1, 2, 30, 7, 32);
        GUI = WORKBENCH_INTERFACE;

        crafts = new ArrayList<>();

        crafts.add(new Crafting.craft(
                new int[]{ID.get("item:planks"), 2},
                new int[][]{{ID.get("item:handsaw"), 1}, {ID.get("item:log"), 1}}));

        ((Slot) GUI.getElement("CRAFT_HANDSAW_RESULT")).setSF(new Slot.SlotFunctions() {
            @Override
            public boolean isValid(int id, int count, int data, String FromSlotWithId) {
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
        });
    }

    @Override
    public void tick(Tile tile) {
        Tile toolWall = findToolWall(tile);
        tile.getContainer("CRAFT_HANDSAW_RESULT").setItem(0, 0, 0);
        for (Crafting.craft craft : crafts) {
            if (craft.ingr[0][0] == ID.get("item:handsaw") && toolWall != null) {
                if (toolWall.getContainer("HandsawSlot").getId() == ID.get("item:handsaw") &&
                        tile.getContainer("CRAFT_HANDSAW_INGREDIENT").getId() == craft.ingr[1][0]) {
                    tile.getContainer("CRAFT_HANDSAW_RESULT").setItem(craft.result[0], craft.result[1] * tile.getContainer("CRAFT_HANDSAW_INGREDIENT").getCount(), 0);
                }
            }
        }
    }

    private void craft(int craftId) {
        Tile tile = MAPS.getTile(GUI.currX, GUI.currY, GUI.currZ);
        if (craftId == 0) {
            PLAYER.inventory.addItem(tile.getContainer("CRAFT_HANDSAW_RESULT"));
            tile.getContainer("CRAFT_HANDSAW_INGREDIENT").setItem(0, 0, 0);

        }
    }

    private static Tile findToolWall(Tile tile) {
        Tile toolWall = null;
        if (MAPS.getTile(tile.x - 1, tile.y, tile.z).id == ID.get("block:tool_wall")) {
            toolWall = MAPS.getTile(tile.x - 1, tile.y, tile.z);
        }
        if (MAPS.getTile(tile.x + 1, tile.y, tile.z).id == ID.get("block:tool_wall")) {
            toolWall = MAPS.getTile(tile.x + 1, tile.y, tile.z);
        }
        return toolWall;
    }
}
