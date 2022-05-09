package com.kgc.sauw.game.os;

import com.jvmfrog.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;
import com.kgc.sauw.os.commands.Command;
import com.kgc.sauw.os.commands.Description;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

@RegistryMetadata("hack")
@Description("Просто поломает игру... возможно... а возможно и нет")
public class HackSAUWCommand extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        PLAYER.health = 99999999999f;
        PLAYER.hunger.hunger = 99999999999f;
    }
}
