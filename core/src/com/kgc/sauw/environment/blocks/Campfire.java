package com.kgc.sauw.environment.blocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.config.BlockConfiguration;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.environment.Environment.BLOCKS;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Campfire extends Block {
    TextureRegion[][] campfireTextures;
    TextureRegion campfireTexture;

    Animation campfireAnimation;


    public Campfire() {
        super(15, TEXTURES.campfire);

        BlockConfiguration.setTransparent(true);
        BlockConfiguration.setLightingRadius(4);
        BlockConfiguration.setLightingColor(new Color(0.8f, 0.6f, 0, 0.5f));
        BlockConfiguration.setCollisionsRectangleByPixels(8, 2, 18, 10, 32);

        campfireTexture = TextureRegion.split(t0, t0.getWidth(), t0.getHeight())[0][0];
        campfireTextures = TextureRegion.split(TEXTURES.campfire_animation, TEXTURES.campfire_animation.getWidth() / 4, TEXTURES.campfire_animation.getHeight());
        campfireAnimation = new Animation(0.7f, campfireTextures[0][0], campfireTextures[0][1], campfireTextures[0][2], campfireTextures[0][3]);
    }

    @Override
    public void tick(Tile tile) {
        tile.t = campfireAnimation.getKeyFrame(BLOCKS.stateTime, true);
    }
}
