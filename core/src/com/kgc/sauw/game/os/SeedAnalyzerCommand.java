package com.kgc.sauw.game.os;

import com.intkgc.curve.registry.RegistryMetadata;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.game.items.BagOfSeeds;
import com.kgc.sauw.os.SAUWOS;
import com.kgc.sauw.os.commands.Command;

import static com.kgc.sauw.core.entity.EntityManager.PLAYER;

@RegistryMetadata("analyze-seeds")
public class SeedAnalyzerCommand extends Command {
    @Override
    public void run(SAUWOS os, String[] args) {
        Item item = PLAYER.getCarriedItem();
        if (item instanceof BagOfSeeds) {
            Container container = PLAYER.inventory.containers.get(PLAYER.hotbar[PLAYER.carriedSlot]);

            System.out.println("IT WORKS???");
        }
    }
}
