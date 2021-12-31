package com.kgc.sauw.game.blocks;

import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.resource.Resource;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.core.environment.Environment.getWorld;

public class Table extends Block {
    public Table() {
        super(Resource.getTexture("Blocks/table.png"));

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(1, 2, 30, 7, 32);
    }

    @Override
    public void tick(Tile tile) {
        if (getWorld().map.getTile(tile.x - 1, tile.y, tile.z).id == SAUW.getId("block:tool_wall") || getWorld().map.getTile(tile.x + 1, tile.y, tile.z).id == SAUW.getId("block:tool_wall")) {
            getWorld().map.setBlock(tile.x, tile.y, tile.z, SAUW.getId("block:workbench"));
        }
    }
}
