package com.kgc.sauw.os.commands;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("echo")
public class Echo extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        if (args.length != 0)
            os.console.print(args[0]);
        else
            System.out.println("???");
    }
}
