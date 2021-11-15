package com.kgc.sauw.core.entity.entities.drop;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.entity.Entity;
import com.kgc.sauw.core.entity.EntityFactory;

public class DropFactory extends EntityFactory {
    public DropFactory() {
        super(GameContext.SAUW, "drop");
    }

    @Override
    protected Entity createEntity() {
        return new Drop();
    }
}
