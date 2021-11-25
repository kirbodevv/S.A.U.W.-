package com.kgc.sauw.core.entity;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.physic.Physic;

public abstract class EntityFactory {
    private final int id;

    public EntityFactory(GameContext context, String stringId) {
        this.id = context.registerId("entity:" + stringId);
    }

    public int getId() {
        return id;
    }

    protected abstract Entity createEntity();

    public final Entity create() {
        Entity entity = createEntity();
        entity.setBody(Physic.createRectangleBody(0, 0, entity.getBodySize().x, entity.getBodySize().y, BodyDef.BodyType.DynamicBody, true));

        entity.setId(id);
        return entity;
    }
}
