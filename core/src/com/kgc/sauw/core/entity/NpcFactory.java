package com.kgc.sauw.core.entity;

import com.kgc.sauw.game.entity.npc.Egor;

public class NpcFactory extends EntityFactory {

    public NpcFactory() {
        super("npc");
    }

    @Override
    protected Entity createEntity() {
        return new Egor();
    }
}
