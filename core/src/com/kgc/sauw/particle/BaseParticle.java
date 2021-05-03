package com.kgc.sauw.particle;

import com.badlogic.gdx.graphics.Texture;

import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;

public class BaseParticle {
    public String id;
    private Texture particle;
    private float width = BLOCK_SIZE / 8f, height = BLOCK_SIZE / 8f;

    public void setTexture(Texture particle) {
        this.particle = particle;
    }

    public void draw(Particles.Particle p) {
        BATCH.draw(p.texture, p.x - p.width / 2f, p.y - p.height / 2f, p.width, p.height);
    }

    public Texture getTexture() {
        return particle;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
