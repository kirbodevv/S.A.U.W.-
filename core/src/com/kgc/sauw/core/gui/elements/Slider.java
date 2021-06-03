package com.kgc.sauw.core.gui.elements;

import com.kgc.sauw.resource.TextureGenerator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.kgc.sauw.core.utils.Camera2D;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.gui.InterfaceElement;

public class Slider extends InterfaceElement {
    private Texture background, slider;
    private int sliderW;
    private int maxValue = 100;
    private int value = 0;
    private double sc;
    private EventListener EL;
    private boolean verticalSlider;

    public Slider(int x, int y, int w, int h) {
        verticalSlider = w <= h;
        int ww = Gdx.graphics.getWidth();
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        if (!verticalSlider) sliderW = h / 2;
        else sliderW = w / 2;
        if (!verticalSlider) {
            slider = TextureGenerator.generateTexture(sliderW / (ww / 16.0f), h / (ww / 16.0f), true);
            background = TextureGenerator.generateTexture(w / (ww / 16.0f), h / 2 / (ww / 16.0f), new Color(0x383838FF), new Color(0x000000FF), new Color(0x000000FF), new Color(0x000000FF));
        } else {
            slider = TextureGenerator.generateTexture(w / (ww / 16.0f), sliderW / (ww / 16.0f), true);
            background = TextureGenerator.generateTexture(w / 2 / (ww / 16.0f), h / (ww / 16.0f), new Color(0x383838FF), new Color(0x000000FF), new Color(0x000000FF), new Color(0x000000FF));
        }
        setMaxValue(100);
    }

    public void setValue(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public void setMaxValue(int v) {
        this.maxValue = v;
        if (!verticalSlider)
            sc = (width + 0.0) / maxValue;
        else sc = (height + 0.0) / maxValue;
    }

    @Override
    public void tick(Camera2D cam) {
        if (isTouched()) {
            if (!verticalSlider)
                value = (int) ((Gdx.input.getX() - x) / sc);
            else
                value = (int) ((Gdx.input.getY() - y) / sc);

            if (value < 0) value = 0;
            if (value > maxValue) value = maxValue;
        }
        if (EL != null) EL.onValueChange(value);
    }

    @Override
    public void renderTick(SpriteBatch b, Camera2D cam) {
        if (!verticalSlider) {
            b.draw(background, cam.X + x, cam.Y + y + height / 4, width, height / 2);
            b.draw(slider, cam.X + x + (int) (sc * value) - sliderW / 2, cam.Y + y, sliderW, height);
        } else {
            b.draw(background, cam.X + x + width / 4, cam.Y + y, width / 2, height);
            b.draw(slider, cam.X + x, cam.Y + y + (height - (int) (sc * value)) - sliderW / 2, width, sliderW);
        }
    }

    public void setEventListener(EventListener EL) {
        this.EL = EL;
    }

    public static abstract class EventListener {
        public abstract void onValueChange(int v);
    }
}
