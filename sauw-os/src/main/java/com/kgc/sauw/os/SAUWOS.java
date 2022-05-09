package com.kgc.sauw.os;

import com.jvmfrog.curve.registry.Registry;
import com.kgc.sauw.os.commands.Shutdown;
import com.kgc.sauw.os.commands.*;
import com.kgc.sauw.os.input.InputListener;
import com.kgc.sauw.os.process.Process;
import com.kgc.sauw.os.terminal.SystemTerminal;
import com.kgc.sauw.os.user.User;

import java.util.HashMap;

public class SAUWOS {
    //TODO: IDManager is static. Can replace commands from another running OS
    public final Registry<Command> commandRegistry = new Registry<>("command");
    public final Registry<User> userRegistry = new Registry<>("user");

    public final HashMap<String, Process> runningProcess = new HashMap<>();

    public final SystemTerminal systemTerminal = new SystemTerminal();
    public final InputListener input = new InputListener(this, systemTerminal);
    public final CommandProcessor commandProcessor = new CommandProcessor();

    public SAUWOS() {
        commandRegistry.register(new Exit());
        commandRegistry.register(new Echo());
        commandRegistry.register(new Shutdown());
        commandRegistry.register(new Help());

        userRegistry.register(new User("root"), "root");
    }

    public void tick() {
    }

    public void start() {
        input.start();
    }

    public void shutdown() {
    }
}
