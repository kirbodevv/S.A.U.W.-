package com.kgc.sauw.UI.Elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.UI.InterfaceElement;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;

public class Text extends InterfaceElement {
    private Texture background;
    private String txt = "";
    private static final BitmapFont bitmapFont;
    private static final float capHeight;

    static {
        bitmapFont = new BitmapFont(Gdx.files.internal("ttf.fnt"));
        capHeight = bitmapFont.getCapHeight();
    }

    Color textColor = new Color(64f / 255, 137f / 255, 154f / 255, 1);

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        if (background != null) background.dispose();
        background = Textures.generateTexture(width / BLOCK_SIZE, height / BLOCK_SIZE, true);
    }

    public void setText(String text) {
        txt = text;
        setTextScale();
        if (bitmapFont.getBounds(text).width > this.width) {
            setSize(height / 2 + (int) bitmapFont.getBounds(text).width, this.height);
        }
    }

    public void setTextScale() {
        if (height != 0)
            bitmapFont.setScale(height / 2f / capHeight);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }


    @Override
    public void render(SpriteBatch batch, Camera2D cam) {
        setTextScale();
        bitmapFont.setColor(textColor);
        batch.draw(background, cam.X + X, cam.Y + Y, width, height);
        bitmapFont.drawMultiLine(batch, txt, cam.X + X, cam.Y + Y + (height / 4 * 3), width, BitmapFont.HAlignment.CENTER);
    }
}
