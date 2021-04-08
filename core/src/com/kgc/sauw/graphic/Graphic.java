package com.kgc.sauw.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

public final class Graphic {
    public static final SpriteBatch BATCH;
    public static final int BLOCK_SIZE;
    public static final Textures TEXTURES;
    public static final Camera2D GAME_CAMERA;
    public static final Camera2D INTERFACE_CAMERA;
    public static final Camera2D MENU_CAMERA;
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    static {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        BATCH = new SpriteBatch();
        TEXTURES = new Textures();
        TEXTURES.load();
        BLOCK_SIZE = Gdx.graphics.getWidth() / 16;
        GAME_CAMERA = new Camera2D();
        GAME_CAMERA.setCameraZoom(1.25f);
        INTERFACE_CAMERA = new Camera2D();
        MENU_CAMERA = new Camera2D();
    }
}
