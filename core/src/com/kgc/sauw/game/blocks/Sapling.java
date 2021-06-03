package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.map.Tile;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.map.World.WORLD;

public class Sapling extends Block {
    TextureRegion[][] saplingTextures;

    public Sapling() {
        super(ID.registeredId("block:sapling", 13), TEXTURES.sapling);

        blockConfiguration.setSize(1, 2);
        blockConfiguration.setTransparent(true);
        blockConfiguration.setInstrumentType(2);
        blockConfiguration.setCollisionsRectangleByPixels(15, 0, 4, 4, 32);

        saplingTextures = TextureRegion.split(t0, t0.getWidth() / 4, t0.getHeight());
    }

    @Override
    public void onPlace(Tile tile) {
        tile.setExtraData("age", 0);
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
            WORLD.setBlock(tile.x, tile.y, tile.z, 6);
        }
    }
}
