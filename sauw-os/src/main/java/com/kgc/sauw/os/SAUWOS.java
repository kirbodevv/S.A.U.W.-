package com.kgc.sauw.os;

import com.jvmfrog.curve.registry.Registry;
import com.kgc.sauw.os.commands.Command;
import com.kgc.sauw.os.commands.Echo;
import com.kgc.sauw.os.commands.Exit;
import com.kgc.sauw.os.commands.Shutdown;
import com.kgc.sauw.os.input.CmdInputListener;
import com.kgc.sauw.os.process.Process;

import java.util.HashMap;

public class SAUWOS {
    //TODO: IDManager is static. Can replace commands from another running OS
    public final Registry<Command> commandRegistry = new Registry<>("command");
    public final HashMap<String, Process> runningProcess = new HashMap<>();
    public final CmdInputListener input = new CmdInputListener(this);
    public final Console console = new Console();

    public SAUWOS() {
        commandRegistry.register(new Exit());
        commandRegistry.register(new Echo());
        commandRegistry.register(new Shutdown());
    }

    public void tick() {
    }

    public void start() {
        input.start();
    }

    public void shutdown() {

    }
}
