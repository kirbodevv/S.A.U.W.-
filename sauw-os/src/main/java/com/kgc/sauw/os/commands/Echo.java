package com.kgc.sauw.os.commands;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("echo")
@Description("Display the arguments")
public class Echo extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        if (args.length != 0)
            os.commandProcessor.print(args[0]);
        else
            System.out.println("???");
    }
}
