package com.kgc.sauw.os.commands;

import com.intkgc.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("exit")
public class Exit extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        os.input.close();
    }
}
