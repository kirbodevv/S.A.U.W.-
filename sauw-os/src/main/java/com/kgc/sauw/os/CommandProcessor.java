package com.kgc.sauw.os;

import com.jvmfrog.curve.registry.IDManager;
import com.kgc.sauw.os.commands.Command;
import org.apache.tools.ant.types.Commandline;

import java.text.MessageFormat;
import java.util.Arrays;

public class CommandProcessor {
    public void execute(String commandLine, SAUWOS os) {
        if (commandLine.startsWith("/")) {
            String[] args = Commandline.translateCommandline(commandLine);
            String commandStringID = "command:" + args[0].substring(1);
            int commandID = -1;
            try {
                commandID = IDManager.get(commandStringID);
            } catch (Exception ignored) {
                print(MessageFormat.format("command \"{0}\" not found", args[0].substring(1)));
            }
            if (commandID != -1) {
                Command command = os.commandRegistry.get(commandID);
                command.run(os, Arrays.copyOfRange(args, 1, args.length));
            }
        }
    }

    public void print(String line) {
        System.out.println(line);
    }
}
