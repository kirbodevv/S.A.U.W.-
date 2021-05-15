package com.kgc.sauw.entity;

public class DropFactory extends EntityFactory{
    public DropFactory() {
        super("drop");
    }

    @Override
    protected Entity createEntity() {
        return new Drop();
    }
}
