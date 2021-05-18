package com.kgc.sauw.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.graphic.Animator;
import com.kgc.sauw.utils.ID;

import java.util.HashMap;

import static com.kgc.sauw.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Particles {
    public static class Particle {
        int id;
        float startX, startY, x, y, duration;
        float width = BLOCK_SIZE / 8f, height = BLOCK_SIZE / 8f;
        Texture texture;

        public void createParticle(int id, float x, float y, float duration) {
            this.id = id;
            startX = x;
            startY = y;
            this.x = x;
            this.y = y;
            this.duration = duration;
            this.width = particles.get(id).getWidth();
            this.height = particles.get(id).getHeight();
            this.texture = particles.get(id).getTexture();
        }

        public void clear() {
            id = -1;
            x = 0;
            y = 0;
            startX = 0;
            startY = 0;
            duration = 0;
        }
    }

    private static final HashMap<Integer, BaseParticle> particles = new HashMap<>();
    private static final Animator animator;
    private static final Particle[] particlesList = new Particle[200];

    static {
        for (int i = 0; i < particlesList.length; i++) {
            particlesList[i] = new Particle();
            particlesList[i].clear();
        }
        animator = new Animator();

        FlyingParticle flameParticle = new FlyingParticle();
        RandomFlyingParticle smokeParticle = new RandomFlyingParticle();

        flameParticle.setTexture(TEXTURES.flameParticle);

        smokeParticle.setTextures(TEXTURES.smokeParticle_0, TEXTURES.smokeParticle_1);
        smokeParticle.setWidth(0.75f);
        smokeParticle.setHeight(0.75f);

        createParticle(flameParticle, "particle:flame");
        createParticle(smokeParticle, "particle:smoke");
    }

    public static void render() {
        for (Particle p : particlesList) {
            if (p.id != -1) {
                particles.get(p.id).draw(p);
            }
            p.duration -= Gdx.graphics.getRawDeltaTime();
            if (p.duration <= 0) {
                p.clear();
            }
        }
    }

    public static Particle addParticle(String id, float x, float y, float duration) {
        for (Particle p : particlesList) {
            if (p.id == -1) {
                p.createParticle(ID.get(id), x, y, duration);
                return p;
            }
        }
        return null;
    }

    public static void createParticle(BaseParticle particle, String id) {
        particles.put(ID.registeredId(id), particle);
    }
}
