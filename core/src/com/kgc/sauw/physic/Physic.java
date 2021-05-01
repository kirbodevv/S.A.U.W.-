package com.kgc.sauw.physic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Physic {
    private static final World world = new World(new Vector2(0, 0), false);

    public static void init() {

    }

    public static void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    public static Body createRectangleBody(float posX, float posY, float boxW, float boxH, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(posX + boxW / 2.f, posY + boxH / 2.f);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxW / 2.f, boxH / 2.f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public static void destroyBody(Body body) {
        world.destroyBody(body);
    }

    public static World getWorld() {
        return world;
    }
}
