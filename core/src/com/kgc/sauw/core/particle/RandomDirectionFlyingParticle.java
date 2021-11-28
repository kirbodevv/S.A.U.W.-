package com.kgc.sauw.core.particle;

import com.badlogic.gdx.math.MathUtils;

public class RandomDirectionFlyingParticle extends FlyingParticle {
    @Override
    public void draw(Particles.Particle p) {
        p.x = MathUtils.lerp(p.x, p.startX + p.direction, 0.05f);
        super.draw(p);
    }

    @Override
    public void createParticle(Particles.Particle particle) {
        particle.direction = (float) Math.random() - 0.5f;
    }
}
