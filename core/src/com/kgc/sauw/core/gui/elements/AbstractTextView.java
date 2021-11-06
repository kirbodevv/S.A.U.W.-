package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.core.utils.languages.Languages;

import static com.kgc.sauw.core.graphic.Graphic.*;

public abstract class AbstractTextView extends InterfaceElement {
    protected String text = "";
    private boolean scalable = true;
    private final Color textColor = new Color(TEXT_COLOR);

    public void setScalable(boolean scalable) {
        this.scalable = scalable;
    }

    public void setTextColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }

    public void setTextColor(Color color) {
        this.textColor.set(color);
    }

    public void setDefaultText() {
        setText(Languages.getString(id));
    }

    public void setText(String text) {
        this.text = text;
        GLYPH_LAYOUT.setText(BITMAP_FONT, text);
        updateTextScale();
        if (scalable && GLYPH_LAYOUT.width > this.width) {
            setSize(height / 2 + (int) GLYPH_LAYOUT.width, this.height);
        }
    }

    public String getText() {
        return text;
    }

    private void updateTextScale() {
        if (height != 0)
            BITMAP_FONT.getData().setScale(height / 2f / BITMAP_FONT_CAP_HEIGHT);
    }

    public abstract void preRender(SpriteBatch batch, Camera2D cam);

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        preRender(batch, cam);
        updateTextScale();
        BITMAP_FONT.setColor(textColor);
        BITMAP_FONT.draw(batch, text, cam.X + x, cam.Y + y + (height / 4 * 3), width, Align.center, false);
    }
}