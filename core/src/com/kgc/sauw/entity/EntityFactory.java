package com.kgc.sauw.entity;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kgc.sauw.physic.Physic;
import com.kgc.sauw.utils.ID;

public abstract class EntityFactory {
    private int id;

    public EntityFactory(String stringId, int id) {
        this.id = ID.registeredId("entity:" + stringId, id);
    }

    public EntityFactory(String stringId) {
        this.id = ID.registeredId("entity:" + stringId);
    }

    public int getId() {
        return id;
    }

    protected abstract Entity createEntity();

    public final Entity create() {
        Entity entity = createEntity();
        entity.setBody(Physic.createRectangleBody(0, 0, entity.getBodySize().x, entity.getBodySize().y, BodyDef.BodyType.DynamicBody));

        entity.setId(id);
        return entity;
    }
}
