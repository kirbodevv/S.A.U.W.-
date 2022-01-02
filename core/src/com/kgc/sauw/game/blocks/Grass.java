package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.item.InstrumentItem;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;

public class Grass extends Block {
    private final Texture flowersTexture = Resource.getTexture("blocks/flowers.png");

    public Grass() {
        super(Resource.getTexture("blocks/grass_1.png"));

        blockConfiguration.setInstrumentType(InstrumentItem.Type.SHOVEL);
    }

    @Override
    public void renderTick(Tile tile) {
        super.renderTick(tile);
        if ((boolean) tile.getExtraData("isFlower")) {
            BATCH.draw(flowersTexture, tile.x, tile.y, 1, 1);
        }
    }

    @Override
    public void setDefaultExtraData(Tile tile) {
        tile.setExtraData("isFlower", false);
    }
}
