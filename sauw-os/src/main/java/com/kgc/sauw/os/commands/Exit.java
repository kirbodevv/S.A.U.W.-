package com.kgc.sauw.os.commands;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("exit")
@Description("DON'T USE, kills the input listener")
public class Exit extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        os.input.close();
    }
}
