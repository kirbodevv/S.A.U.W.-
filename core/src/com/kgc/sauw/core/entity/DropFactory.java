package com.kgc.sauw.core.entity;

public class DropFactory extends EntityFactory{
    public DropFactory() {
        super("drop");
    }

    @Override
    protected Entity createEntity() {
        return new Drop();
    }
}
