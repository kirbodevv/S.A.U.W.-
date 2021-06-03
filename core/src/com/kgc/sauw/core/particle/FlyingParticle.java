package com.kgc.sauw.core.particle;

import com.badlogic.gdx.math.MathUtils;

public class FlyingParticle extends BaseParticle {
    @Override
    public void draw(Particles.Particle p) {
        p.y = MathUtils.lerp(p.y, p.startY + 1f, 0.01f);
        super.draw(p);
    }
}