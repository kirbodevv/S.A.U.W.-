package com.kgc.bluesgui.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;;
import com.kgc.bluesgui.InterfaceElement;
import com.kgc.utils.Camera2D;

public class Image extends InterfaceElement {
    private final Sprite sprite = new Sprite();

    public void setSprite(Sprite sprite) {
        this.sprite.set(sprite);
    }

    public void setImg(Texture t) {
        this.sprite.setRegion(t);
    }

    public void setImg(TextureRegion textureRegion) {
        this.sprite.setRegion(textureRegion);
    }

    @Override
    public void setSize(float w, float h) {
        super.setSize(w, h);
        sprite.setSize(w, h);
    }

    @Override
    protected void tick(Camera2D cam) {
    }

    @Override
    public void renderTick(SpriteBatch batch, Camera2D cam) {
        sprite.setPosition(cam.X + x, cam.Y + y);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onClick(boolean onElement) {
    }
}
