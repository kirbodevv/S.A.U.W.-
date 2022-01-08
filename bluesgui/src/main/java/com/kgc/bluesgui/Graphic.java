package com.kgc.bluesgui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.utils.Camera2D;

public class Graphic {
    public static final SpriteBatch BATCH;
    public static final Camera2D INTERFACE_CAMERA;
    public static final Color TEXT_COLOR;
    public static float BLOCK_SIZE;
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;
    public static float WIDTH_IN_BLOCKS;
    public static float HEIGHT_IN_BLOCKS;

    public static BitmapFont BITMAP_FONT;
    public static final GlyphLayout GLYPH_LAYOUT;
    public static final float BITMAP_FONT_CAP_HEIGHT;
    public static Texture close_button_texture;

    static {
        TEXT_COLOR = new Color(0xAC9262FF);
        BATCH = new SpriteBatch();
        BATCH.enableBlending();
        INTERFACE_CAMERA = new Camera2D();

        BITMAP_FONT = new BitmapFont();
        GLYPH_LAYOUT = new GlyphLayout();
        BITMAP_FONT_CAP_HEIGHT = BITMAP_FONT.getCapHeight();
        close_button_texture = new Texture(Gdx.files.internal("close_button.png"));
    }

    public static void createBitmapFont(FileHandle ttf) {
        if (BITMAP_FONT != null) BITMAP_FONT.dispose();
        BITMAP_FONT = new BitmapFont(ttf);
    }

    public static void resize(float w, float h) {
        SCREEN_WIDTH = w;
        SCREEN_HEIGHT = h;
        BLOCK_SIZE = h / (w / 16) >= 9 ? w / 16f : h / 9f;
        WIDTH_IN_BLOCKS = SCREEN_WIDTH / BLOCK_SIZE;
        HEIGHT_IN_BLOCKS = SCREEN_HEIGHT / BLOCK_SIZE;
        Interfaces.resize();
    }

    public static void dispose() {
        Skins.dispose();
    }
}
