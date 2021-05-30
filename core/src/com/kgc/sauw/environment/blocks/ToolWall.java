package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.gui.Interfaces.TOOL_WALL_INTERFACE;

public class ToolWall extends Block {
    TextureRegion[][] instrumentsTextures;

    public ToolWall() {
        super(ID.registeredId("block:tool_wall"), TEXTURES.toolWall);

        instrumentsTextures = TextureRegion.split(TEXTURES.toolWallInstruments, TEXTURES.toolWallInstruments.getWidth() / 4, TEXTURES.toolWallInstruments.getHeight());

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(2, 0, 30, 7, 32);

        GUI = TOOL_WALL_INTERFACE;
    }

    @Override
    public void renderTick(Tile tile) {
        super.renderTick(tile);
        if (tile.getContainer("HammerSlot").getId() == ID.get("item:hammer"))
            BATCH.draw(instrumentsTextures[0][0], tile.x, tile.y, 1, 1);
        if (tile.getContainer("HandsawSlot").getId() == ID.get("item:handsaw"))
            BATCH.draw(instrumentsTextures[0][2], tile.x, tile.y, 1, 1);
    }
}
