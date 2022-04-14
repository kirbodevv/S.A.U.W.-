package com.kgc.sauw.os.commands;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("shutdown")
public class Shutdown extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        os.shutdown();
    }
}
