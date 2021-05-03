package com.kgc.sauw.particle;

import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class RandomFlyingParticle extends FlyingParticle {
    Texture[] textures;
    Random random = new Random();

    public void setTextures(Texture... t) {
        textures = new Texture[t.length];
        System.arraycopy(t, 0, textures, 0, t.length);
    }

    @Override
    public Texture getTexture() {
        int v = random.nextInt(textures.length);
        return textures[v];
    }
}
