package com.kgc.sauw.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.gui.InterfaceElement;
import com.kgc.sauw.utils.Camera2D;

import static com.kgc.sauw.graphic.Graphic.*;

public class Text extends InterfaceElement {
    private Texture background;
    private String txt = "";
    private final Color textColor = new Color(64f / 255, 137f / 255, 154f / 255, 1);

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (background != null) background.dispose();
        background = Textures.generateTexture(width / BLOCK_SIZE, height / BLOCK_SIZE, true);
    }

    public void setText(String text) {
        txt = text;
        setTextScale();
        if (BITMAP_FONT.getBounds(text).width > this.width) {
            setSize(height / 2 + (int) BITMAP_FONT.getBounds(text).width, this.height);
        }
    }

    public void setTextScale() {
        if (height != 0)
            BITMAP_FONT.setScale(height / 2f / BITMAP_FONT_CAP_HEIGHT);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }


    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        setTextScale();
        BITMAP_FONT.setColor(textColor);
        batch.draw(background, cam.X + X, cam.Y + Y, width, height);
        BITMAP_FONT.drawMultiLine(batch, txt, cam.X + X, cam.Y + Y + (height / 4 * 3), width, BitmapFont.HAlignment.CENTER);
    }
}