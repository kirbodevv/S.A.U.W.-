package com.kgc.sauw.os.commands;

import com.intkgc.curve.registry.RegistryObject;
import com.kgc.sauw.os.SAUWOS;

public abstract class Command extends RegistryObject {
    public abstract void run(SAUWOS os, String[] args);

    @Override
    public void init() {

    }
}
