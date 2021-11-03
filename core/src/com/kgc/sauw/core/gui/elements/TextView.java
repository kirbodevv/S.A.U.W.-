package com.kgc.sauw.core.gui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.core.gui.ElementSkin;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.sauw.core.utils.Camera2D;

public class TextView extends AbstractTextView {
    private ElementSkin background;

    public TextView() {
        setStandardBackground(true);
    }

    public void setBackground(ElementSkin background) {
        this.background = background;
    }

    public void setStandardBackground(boolean b) {
        setBackground(b ? Skins.round_up : Skins.round_down_1);
    }

    @Override
    public void preRender(SpriteBatch batch, Camera2D cam) {
        if (background != null) background.draw(cam.X + x, cam.Y + y, width, height);
    }

    @Override
    protected void tick(Camera2D cam) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }
}