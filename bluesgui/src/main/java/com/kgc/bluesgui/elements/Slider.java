package com.kgc.bluesgui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.bluesgui.ElementSkin;
import com.kgc.bluesgui.InterfaceElement;
import com.kgc.bluesgui.Skins;
import com.kgc.utils.Camera2D;

public class Slider extends InterfaceElement {
    private ElementSkin background, slider;
    private float sliderW;
    private int maxValue = 100;
    private int value = 0;
    private double sc;
    private EventListener EL;
    private boolean verticalSlider;

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        int ww = Gdx.graphics.getWidth();
        if (!verticalSlider) sliderW = h / 2;
        else sliderW = w / 2;
        slider = Skins.getSkin("slider_slider");
        slider = Skins.getSkin("slider_background");
        verticalSlider = w <= h;
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
        background.draw(cam.X + x, cam.Y + y + height / 4, width, height / 2);
        slider.draw(cam.X + x + (int) (sc * value) - sliderW / 2, cam.Y + y, sliderW, height);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }

    public void setEventListener(EventListener EL) {
        this.EL = EL;
    }

    public static abstract class EventListener {
        public abstract void onValueChange(int v);
    }
}
