package com.kgc.sauw.core.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.COLOR_ALPHA;

public class ElementSkin {
    NinePatchDrawable drawable = null;
    Texture texture = null;
    private final Color color = new Color(1, 1, 1, 1);

    public Color getColor() {
        return color;
    }

    public ElementSkin() {
    }

    public ElementSkin(ElementSkin elementSkin) {
        this.drawable = elementSkin.drawable;
        this.texture = elementSkin.texture;
        this.color.set(elementSkin.color);
    }

    public ElementSkin(Texture texture, int outlineSize) {
        drawable = createDrawableFromTexture(texture, outlineSize);
    }

    public ElementSkin(Texture texture) {
        this.texture = texture;
    }

    public void setColor(int rgba) {
        color.set(rgba);
    }

    public void draw(float x, float y, float w, float h, Color startColor, Color endColor) {
        draw(x, y, w, h, startColor, endColor, 0.1f);
    }

    public void draw(float x, float y, float w, float h, Color startColor, Color endColor, float progress) {
        Color color = startColor.lerp(endColor, progress);
        draw(x, y, w, h, color);
    }

    public void draw(float x, float y, float w, float h, Color color) {
        Color batchColor = new Color(BATCH.getColor());
        color.a = COLOR_ALPHA;
        BATCH.setColor(color);
        if (drawable != null)
            drawable.draw(BATCH, x, y, w, h);
        if (texture != null)
            BATCH.draw(texture, x, y, w, h);
        BATCH.setColor(batchColor);
    }

    public void draw(float x, float y, float w, float h) {
        draw(x, y, w, h, color);
    }

    public static NinePatchDrawable createDrawableFromTexture(Texture texture, int outlineSize) {
        NinePatch ninePatch = new NinePatch(texture, outlineSize, outlineSize, outlineSize, outlineSize);
        return new NinePatchDrawable(ninePatch);
    }
}