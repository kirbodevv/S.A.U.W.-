package com.kgc.sauw.core.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.core.math.Maths;
import com.kgc.sauw.core.resource.Resource;

import java.util.HashMap;

import static com.kgc.sauw.core.GameContext.SAUW;
import static com.kgc.sauw.core.graphic.Graphic.BLOCK_SIZE;
import static com.kgc.sauw.core.graphic.Graphic.GAME_CAMERA;

public class Particles {
    public static class Particle {
        int id;
        float startX, startY, x, y, duration, direction;
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
    private static final Particle[] particlesList = new Particle[1000];

    public static void init() {
        for (int i = 0; i < particlesList.length; i++) {
            particlesList[i] = new Particle();
            particlesList[i].clear();
        }

        RandomDirectionFlyingParticle flameParticle = new RandomDirectionFlyingParticle();
        RandomFlyingParticle smokeParticle = new RandomFlyingParticle();

        flameParticle.setTexture(Resource.getTexture("particle/flame.png"));
        flameParticle.setWidth(0.15f);
        flameParticle.setHeight(0.15f);

        smokeParticle.setTextures(Resource.getTexture("particle/smoke.png"), Resource.getTexture("particle/smoke_1.png"));
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
            p.duration -= Gdx.graphics.getDeltaTime();
            if (p.duration <= 0) {
                p.clear();
            }
        }
    }

    public static void addParticle(String id, float x, float y, float duration) {
        if (Maths.isLiesOnRect(GAME_CAMERA.X, GAME_CAMERA.Y, GAME_CAMERA.W, GAME_CAMERA.H, x, y)) {
            for (Particle p : particlesList) {
                if (p.id == -1) {
                    p.createParticle(SAUW.getId(id), x, y, duration);
                    particles.get(SAUW.getId(id)).createParticle(p);
                    return;
                }
            }
        }
    }

    public static void createParticle(BaseParticle particle, String id) {
        particles.put(SAUW.registerId(id), particle);
    }
}
