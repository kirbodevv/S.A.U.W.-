package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.ui.interfaces.Interfaces.FURNACE_INTERFACE;
import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Furnace extends Block {
    TextureRegion[][] furnaceTextures;
    Animation furnaceAnimation;

    public Furnace() {
        super(11, TEXTURES.furnace);

        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setInstrumentType(1);
        BlockConfiguration.setLightingRadius(2);
        BlockConfiguration.setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
        BlockConfiguration.setCollisionsRectangleByPixels(1, 0, 30, 13, 32);

        furnaceTextures = TextureRegion.split(t0, t0.getWidth() / 4, t0.getHeight());
        furnaceAnimation = new Animation(0.2f, furnaceTextures[0][1], furnaceTextures[0][2], furnaceTextures[0][2]);
        GUI = FURNACE_INTERFACE;
    }

    @Override
    public void onPlace(Tile tile) {
        tile.setExtraData("progress", 0);
        tile.setExtraData("fuel", 0);
        tile.setExtraData("curRecId", 0);
        tile.t = furnaceTextures[0][0];
    }

    @Override
    public void tick(Tile tile) {
        tile.t = furnaceTextures[0][0];
        if ((int) (tile.getExtraData("fuel")) > 0) tile.t = furnaceAnimation.getKeyFrame(BLOCKS.stateTime, true);
    }
}
