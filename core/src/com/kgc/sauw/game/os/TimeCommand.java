package com.kgc.sauw.game.os;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.core.environment.Environment;
import com.kgc.sauw.os.SAUWOS;
import com.kgc.sauw.os.commands.Command;

@RegistryMetadata("time")
public class TimeCommand extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        Environment.getWorld().worldTime.setTime(Integer.parseInt(args[0]));
    }
}
