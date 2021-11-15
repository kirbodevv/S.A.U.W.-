package com.kgc.sauw.game.blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.utils.Resource;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.environment.world.Tile;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.game.gui.Interfaces.TOOL_WALL_INTERFACE;

public class ToolWall extends Block {
    TextureRegion[][] instrumentsTextures;

    public ToolWall() {
        super(SAUW.registeredId("block:tool_wall"), Resource.getTexture("Blocks/tool_wall.png"));

        instrumentsTextures = TextureRegion.split(Resource.getTexture("Blocks/tool_wall_instruments.png"), Resource.getTexture("Blocks/tool_wall_instruments.png").getWidth() / 4, Resource.getTexture("Blocks/tool_wall_instruments.png").getHeight());

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
