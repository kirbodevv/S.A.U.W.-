package com.kgc.sauw.core.gui;

import com.kgc.sauw.core.world.Tile;

import static com.kgc.sauw.core.environment.Environment.getWorld;

public abstract class BlockInterface extends Interface {

    public int currX, currY, currZ;

    public BlockInterface(String ID) {
        super(ID);
    }

    public void open(int x, int y, int z) {
        currX = x;
        currY = y;
        currZ = z;
        this.open();
        onOpen(getWorld().map.getTile(x, y, z));
    }

    @Override
    public void tick() {
        for (int i = 0; i < getWorld().map.getTile(currX, currY, currZ).containers.size(); i++) {
            getSlot(getWorld().map.getTile(currX, currY, currZ).containers.get(i).containerId).setContainer(getWorld().map.getTile(currX, currY, currZ).containers.get(i));
        }
        tick(getWorld().map.getTile(currX, currY, currZ));
    }

    public abstract void tick(Tile tile);

    public abstract void onOpen(Tile tile);
}
