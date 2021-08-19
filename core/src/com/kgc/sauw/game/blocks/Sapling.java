package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.game.items.InstrumentItem;

import static com.kgc.sauw.core.environment.Environment.getWorld;

public class Sapling extends Block {
    TextureRegion[][] saplingTextures;

    public Sapling() {
        super(ID.registeredId("block:sapling", 13), Resource.getTexture("Blocks/sapling.png"));

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setInstrumentType(InstrumentItem.Type.AXE);
        blockConfiguration.setCollisionsRectangleByPixels(15, 0, 4, 4, 32);

        saplingTextures = TextureRegion.split(t0, t0.getWidth() / 4, t0.getHeight());
    }

    @Override
    public void setDefaultExtraData(Tile tile) {
        tile.setExtraData("age", 0);
    }

    @Override
    public void onPlace(Tile tile) {
        tile.t = saplingTextures[0][0];
    }

    @Override
    public void tick(Tile tile) {
        if ((int) tile.getExtraData("age") < 1) {
            tile.t = saplingTextures[0][0];
        } else if ((int) tile.getExtraData("age") < 2) {
            tile.t = saplingTextures[0][1];
        } else if ((int) tile.getExtraData("age") < 3) {
            tile.t = saplingTextures[0][2];
        } else if ((int) tile.getExtraData("age") < 4) {
            tile.t = saplingTextures[0][3];
        }
    }

    @Override
    public void randomTick(Tile tile) {
        tile.setExtraData("age", (int) tile.getExtraData("age") + 1);
        if ((int) tile.getExtraData("age") >= 4) {
            getWorld().map.setBlock(tile.x, tile.y, tile.z, 6);
        }
    }
}
