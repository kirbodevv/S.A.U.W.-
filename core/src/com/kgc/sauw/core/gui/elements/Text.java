package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.skins.Skins;

import static com.kgc.sauw.core.graphic.Graphic.*;

public class Text extends InterfaceElement {
    private ElementSkin background;
    private String txt = "";
    private final Color textColor = new Color(TEXT_COLOR);

    public Text(){
        setStandardBackground(true);
    }

    public void setBackground(ElementSkin background) {
        this.background = background;
    }

    public void setStandardBackground(boolean b) {
        setBackground(b ? Skins.round_up : Skins.round_down_1);
    }
    public void setText(String text) {
        txt = text;
        GLYPH_LAYOUT.setText(BITMAP_FONT, text);
        setTextScale();
        if (GLYPH_LAYOUT.width > this.width) {
            setSize(height / 2 + (int) GLYPH_LAYOUT.width, this.height);
        }
    }

    public void setTextScale() {
        if (height != 0)
            BITMAP_FONT.getData().setScale(height / 2f / BITMAP_FONT_CAP_HEIGHT);
    }

    public void setColor(int r, int g, int b) {
        this.textColor.set(r / 255f, g / 255f, b / 255f, 1f);
    }


    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        setTextScale();
        BITMAP_FONT.setColor(textColor);
        if (background != null) background.draw(cam.X + x, cam.Y + y, width, height);
        BITMAP_FONT.draw(batch, txt, cam.X + x, cam.Y + y + (height / 4 * 3), width, Align.center, false);
    }
}