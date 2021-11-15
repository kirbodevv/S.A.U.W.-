package com.kgc.sauw.game.entity.egor;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.entity.Entity;
import com.kgc.sauw.core.entity.EntityFactory;

public class EgorFactory extends EntityFactory {
    public EgorFactory() {
        super(GameContext.SAUW, "egor");
    }

    @Override
    protected Entity createEntity() {
        return new Egor();
    }
}
