package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.gui.InterfaceElement;
import com.kgc.sauw.core.utils.Camera2D;
import com.kgc.sauw.core.utils.TextureGenerator;
import com.kgc.sauw.core.skins.Skins;

public class ProgressBar extends InterfaceElement {
    private float value;
    private float maxValue;
    private ElementSkin background;
    private ElementSkin foreground;
    private final TextureRegion progress = new TextureRegion();

    private boolean generateTextures;
    private final Color color = new Color(1f, 1f, 1f, 1f);


    public void setColor(int rgba) {
        this.color.set(rgba);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(int r, int g, int b) {
        this.color.set(r / 255f, g / 255f, b / 255f, 1);
    }

    public ProgressBar(boolean generateTextures) {
        this.generateTextures = generateTextures;
        setBackground(Skins.progress_bar_background_round);
        setForeground(Skins.progress_bar_foreground_round);
    }

    public void setBackground(ElementSkin background) {
        this.background = background;
    }

    public void setForeground(ElementSkin foreground) {
        this.foreground = foreground;
    }

    public void setProgressTexture(Texture progress) {
        this.progress.setTexture(progress);
        this.progress.setRegion(0, 0, progress.getWidth(), progress.getHeight());
    }

    public void setValue(float value) {
        this.value = Math.min(value, maxValue);
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getValue() {
        return value;
    }

    public float getMaxValue() {
        return maxValue;
    }

    @Override
    public void setSizeInBlocks(float w, float h) {
        super.setSizeInBlocks(w, h);
        if (generateTextures) {
            setProgressTexture(TextureGenerator.generateProgressBarPTexture(w, h));
        }
    }

    @Override
    protected void tick(Camera2D cam) {
        progress.setRegionWidth((int) (progress.getTexture().getWidth() * (value / maxValue)));
    }

    @Override
    protected void renderTick(SpriteBatch batch, Camera2D cam) {
        Color batchColor = new Color(batch.getColor());
        batch.setColor(1, 1, 1, 1);
        if (background != null) background.draw(x, y, width, height);
        float pixelWidth = width / progress.getTexture().getWidth();
        batch.setColor(color);
        batch.draw(progress, x, y, progress.getRegionWidth() * pixelWidth, height);
        batch.setColor(batchColor);
        if (foreground != null) foreground.draw(x, y, width, height);
    }
}