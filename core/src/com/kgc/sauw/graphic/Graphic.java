package com.kgc.sauw.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public static final float SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT;
    public static final BitmapFont BITMAP_FONT;
    public static final float BITMAP_FONT_CAP_HEIGHT;

    static {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        BATCH = new SpriteBatch();
        TEXTURES = new Textures();
        TEXTURES.load();
        BLOCK_SIZE = (int) (SCREEN_WIDTH / 16);
        GAME_CAMERA = new Camera2D();
        GAME_CAMERA.setCurrentCameraZoom(1.25f);
        INTERFACE_CAMERA = new Camera2D();
        MENU_CAMERA = new Camera2D();
        BITMAP_FONT = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        BITMAP_FONT_CAP_HEIGHT = BITMAP_FONT.getCapHeight();
    }
}
