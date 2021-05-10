package com.kgc.sauw.gui.interfaces.blockInterfaces;

import com.kgc.sauw.gui.Container;
import com.kgc.sauw.gui.Interface;
import com.kgc.sauw.gui.elements.Slot;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class FurnaceInterface extends Interface {
    Slot resultSlot;
    Slot fuelSlot;
    Slot ingSlot;
    int[][] recipes = new int[][]{
            {13, 21}
    };
    int[][] fuel = new int[][]{
            {8, 5}
    };


    public FurnaceInterface() {
        super("FURNACE_INTERFACE");
        setHeaderText(LANGUAGES.getString("furnace")).isBlockInterface(true).createInventory();

        int temp = (int) (width - SCREEN_WIDTH / 24 * 4) / 2;
        resultSlot = new Slot("ResultSlot", this);
        ingSlot = new Slot("IngSlot", this);
        fuelSlot = new Slot("FuelSlot", this);
        resultSlot.setSF(new Slot.SlotFunctions() {
            @Override
            public boolean isValid(int id, int count, int data, String FromSlotWithId) {
                return false;
            }

            @Override
            public void onClick() {

            }

            @Override
            public boolean possibleToDrag() {
                return true;
            }
        });
        Elements.add(resultSlot);
        Elements.add(fuelSlot);
        Elements.add(ingSlot);

        updateElementsList();
    }

    @Override
    public void tick(Tile tile) {
        if ((int) (tile.getExtraData("fuel")) <= 0) {
            for (int i = 0; i < fuel.length; i++) {
                Container fuelCon = tile.getContainer("FuelSlot");
                for (int j = 0; j < recipes.length; j++) {
                    if (fuelCon.getId() == fuel[i][0] && tile.getContainer("IngSlot").getId() == recipes[i][0]) {
                        fuelCon.setItem(fuelCon.getId(), fuelCon.getCount() - 1, fuelCon.getData());
                        tile.setExtraData("fuel", 20 * fuel[i][1]);
                    }
                }
            }
        } else {
            tile.setExtraData("fuel", (int) tile.getExtraData("fuel") - 1);
            if ((int) (tile.getExtraData("progress")) <= 0) {
                for (int i = 0; i < recipes.length; i++) {
                    Container ingCon = tile.getContainer("IngSlot");
                    if (ingCon.getId() == recipes[i][0] && (tile.getContainer("ResultSlot").getId() == recipes[i][1] || tile.getContainer("ResultSlot").getId() == 0) && tile.getContainer("ResultSlot").getCount() < ITEMS.getItemById(recipes[i][1]).getItemConfiguration().maxCount) {
                        tile.setExtraData("progress", 100);
                        tile.setExtraData("curRecId", recipes[i][1]);
                    }
                }
            } else {
                tile.setExtraData("progress", (int) tile.getExtraData("progress") - 1);
                if ((int) tile.getExtraData("progress") <= 0) {
                    Container res = tile.getContainer("ResultSlot");
                    Container ing = tile.getContainer("IngSlot");
                    ing.setItem(ing.getId(), ing.getCount() - 1, ing.getData());
                    res.setItem((int) tile.getExtraData("curRecId"), res.getCount() + 1, res.getData());
                }
            }
        }
    }
}
