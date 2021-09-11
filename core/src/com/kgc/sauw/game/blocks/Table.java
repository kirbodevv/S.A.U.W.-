package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.world.Tile;

import static com.kgc.sauw.core.environment.Environment.getWorld;

public class Table extends Block {
    public Table() {
        super(ID.registeredId("block:table"), Resource.getTexture("Blocks/table.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(1, 2, 30, 7, 32);
    }

    @Override
    public void tick(Tile tile) {
        if (getWorld().map.getTile(tile.x - 1, tile.y, tile.z).id == ID.get("block:tool_wall") || getWorld().map.getTile(tile.x + 1, tile.y, tile.z).id == ID.get("block:tool_wall")) {
            getWorld().map.setBlock(tile.x, tile.y, tile.z, ID.get("block:workbench"));
        }
    }
}
