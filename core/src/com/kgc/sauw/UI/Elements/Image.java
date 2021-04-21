package com.kgc.sauw.UI.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kgc.sauw.UI.InterfaceElement;
import com.kgc.sauw.utils.Camera2D;

public class Image extends InterfaceElement {
    private Texture t;

    public Image(int x, int y, int w, int h) {
        setPosition(x, y);
        setSize(w, h);
    }

    public void setImg(Texture t) {
        this.t = t;
    }

    @Override
    public void render(SpriteBatch batch, Camera2D cam) {
        super.render(batch, cam);
        batch.draw(t, X + cam.X, Y + cam.Y, width, height);
    }
}
