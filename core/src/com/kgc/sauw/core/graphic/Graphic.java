package com.kgc.sauw.core.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.Interface;
import com.kgc.sauw.game.gui.Interfaces;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.core.utils.GameCameraController;

import static com.kgc.sauw.game.gui.Interfaces.HUD;

public final class Graphic {
    public static final SpriteBatch BATCH;
    public static float BLOCK_SIZE;
    public static final Camera2D GAME_CAMERA;
    public static final Camera2D INTERFACE_CAMERA;
    public static final Camera2D MENU_CAMERA;
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;
    public static final BitmapFont BITMAP_FONT;
    public static float WIDTH_IN_BLOCKS;
    public static float HEIGHT_IN_BLOCKS;
    public static final GlyphLayout GLYPH_LAYOUT;
    public static final float BITMAP_FONT_CAP_HEIGHT;
    public static final Color TEXT_COLOR;

    static {
        TEXT_COLOR = new Color(0xAC9262FF);

        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BATCH = new SpriteBatch();

        GAME_CAMERA = new Camera2D();
        INTERFACE_CAMERA = new Camera2D();
        MENU_CAMERA = new Camera2D();

        BITMAP_FONT = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        GLYPH_LAYOUT = new GlyphLayout();
        BITMAP_FONT_CAP_HEIGHT = BITMAP_FONT.getCapHeight();
    }

    public static void resize(int w, int h) {
        INTERFACE_CAMERA.resize(Math.max(w, h));
        MENU_CAMERA.resize(Math.max(w, h));
        GameCameraController.setSize();

        setSize(w, h);

        for (Interface interface_ : Interfaces.INTERFACES) {
            interface_.resize();
            //interface_.resize(); да раньше этот метод вызывался 2 раза
        }
        Slot.itemDamageProgressBar.resize();
        HUD.resize();
    }

    private static void setSize(float w, float h) {
        SCREEN_WIDTH = w;
        SCREEN_HEIGHT = h;
        BLOCK_SIZE = h / (w / 16) >= 9 ? w / 16f : h / 9f;
        WIDTH_IN_BLOCKS = SCREEN_WIDTH / BLOCK_SIZE;
        HEIGHT_IN_BLOCKS = SCREEN_HEIGHT / BLOCK_SIZE;
    }
}
