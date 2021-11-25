package com.kgc.sauw.core.environment.block;

import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.environment.fuel.Fuel;
import com.kgc.sauw.core.environment.world.Tile;

public abstract class AbstractFurnaceBlock extends Block implements HeatingBlock {
    public abstract float getWorkTemperature();

    @Override
    public double heatTemperature(Tile tile) {
        return (double) tile.getExtraData("currentTemperature");
    }

    @Override
    public void tick(Tile tile) {
        Container fuelContainer = tile.getContainer("fuel");
        boolean burning = (int) tile.getExtraData("burnTime") > 0;
        if (!burning) {
            if (Fuel.compareFuel(fuelContainer.id)) {
                tile.setExtraData("burnTime", Fuel.getFuel(fuelContainer.id).duration);
                fuelContainer.decreaseItem();
            }
        } else {
            tile.setExtraData("burnTime", (int) tile.getExtraData("burnTime") - 1);
            tile.setExtraData("currentTemperature", (double) tile.getExtraData("currentTemperature") + 0.1d);
        }
    }

    @Override
    public void setDefaultExtraData(Tile tile) {
        tile.setExtraData("currentTemperature", 0d);
        tile.setExtraData("burnTime", 0);
    }


}
