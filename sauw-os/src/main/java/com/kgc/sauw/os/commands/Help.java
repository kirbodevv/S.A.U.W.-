package com.kgc.sauw.os.commands;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;

@RegistryMetadata("help")
@Description("Display information about commands")
public class Help extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        for (Command command : os.commandRegistry.getObjects()) {
            Class<? extends Command> commandClass = command.getClass();

            String description = "not described";

            if (commandClass.isAnnotationPresent(Description.class)) {
                Description descriptionAnnotation = commandClass.getAnnotation(Description.class);
                description = descriptionAnnotation.value();
            }
            os.commandProcessor.print(command.getStringId() + " - " + description);
        }
    }
}
