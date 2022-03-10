package com.kgc.sauw.game.os;

import com.intkgc.curve.registry.RegistryMetadata;
import com.kgc.sauw.os.SAUWOS;
import com.kgc.sauw.os.commands.Command;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

@RegistryMetadata("hack")
public class HackSAUWCommand extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        PLAYER.health = 99999999999f;
        PLAYER.hunger = 99999999999f;
    }
}
