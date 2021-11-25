package com.kgc.sauw.core.entity.entities.player;

import com.kgc.sauw.core.GameContext;
import com.kgc.sauw.core.entity.Entity;
import com.kgc.sauw.core.entity.EntityFactory;

public class PlayerFactory extends EntityFactory {
    public PlayerFactory() {
        super(GameContext.SAUW, "player");
    }

    @Override
    protected Entity createEntity() {
        return new Player();
    }
}
