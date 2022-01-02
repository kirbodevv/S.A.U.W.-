package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.block.Block;
import com.kgc.sauw.core.resource.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.world.Tile;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.game.gui.Interfaces.TOOL_WALL_INTERFACE;

public class ToolWall extends Block {
    TextureRegion[][] instrumentsTextures;

    public ToolWall() {
        super(Resource.getTexture("blocks/tool_wall.png"));

        instrumentsTextures = TextureRegion.split(Resource.getTexture("blocks/tool_wall_instruments.png"), Resource.getTexture("blocks/tool_wall_instruments.png").getWidth() / 4, Resource.getTexture("blocks/tool_wall_instruments.png").getHeight());

        blockConfiguration.setTransparent(true);
        blockConfiguration.setCollisionsRectangleByPixels(2, 0, 30, 7, 32);

        GUI = TOOL_WALL_INTERFACE;
    }

    @Override
    public void renderTick(Tile tile) {
        super.renderTick(tile);
        if (tile.getContainer("HammerSlot").getId() == SAUW.getId("item:hammer"))
            BATCH.draw(instrumentsTextures[0][0], tile.x, tile.y, 1, 1);
        if (tile.getContainer("HandsawSlot").getId() == SAUW.getId("item:handsaw"))
            BATCH.draw(instrumentsTextures[0][2], tile.x, tile.y, 1, 1);
    }
}
