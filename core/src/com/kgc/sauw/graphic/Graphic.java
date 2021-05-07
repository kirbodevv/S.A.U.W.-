package com.kgc.sauw.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.ui.Interface;
import com.kgc.sauw.ui.interfaces.Interfaces;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.utils.GameCameraController;

import static com.kgc.sauw.ui.interfaces.Interfaces.GAME_INTERFACE;

public final class Graphic {
    public static final SpriteBatch BATCH;
    public static float BLOCK_SIZE;
    public static final Textures TEXTURES;
    public static final Camera2D GAME_CAMERA;
    public static final Camera2D INTERFACE_CAMERA;
    public static final Camera2D MENU_CAMERA;
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;
    public static final BitmapFont BITMAP_FONT;
    public static final float BITMAP_FONT_CAP_HEIGHT;

    static {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        BATCH = new SpriteBatch();
        TEXTURES = new Textures();
        TEXTURES.load();
        BLOCK_SIZE = SCREEN_WIDTH / 16;
        GAME_CAMERA = new Camera2D();
        INTERFACE_CAMERA = new Camera2D();
        MENU_CAMERA = new Camera2D();
        BITMAP_FONT = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        BITMAP_FONT_CAP_HEIGHT = BITMAP_FONT.getCapHeight();
    }

    public static void resize(int w, int h) {
        SCREEN_WIDTH = w;
        SCREEN_HEIGHT = h;
        INTERFACE_CAMERA.resize(Math.max(w, h));
        MENU_CAMERA.resize(Math.max(w, h));
        BLOCK_SIZE = w / 16f;
        GameCameraController.setSize();
        for (Interface Interface : Interfaces.INTERFACES) {
            /*
             * Здесь два раза вызывается метод resize(); из-за темной магии InterfaceAPI,
             * которая после резкого изменения размера окна ломает позиции элементов.
             * Уверен когда-нибудь в будущем когда я случайно исправлю этот баг мне придётся убрать второй вызов
             */
            Interface.resize();
            Interface.resize();
        }
        GAME_INTERFACE.resize();
    }
}
