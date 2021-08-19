package com.kgc.sauw.game.environment;

import com.kgc.sauw.core.item.ItemsArray;
import com.kgc.sauw.game.items.*;

public class Items extends ItemsArray {
    public Items() {
        addItem(new VoidItem());
        addItem(new Grass());
        addItem(new Apple());
        addItem(new Chest());
        addItem(new Stick());
        addItem(new Log());
        addItem(new Stone());
        addItem(new IronOre());
        addItem(new StonePickaxe());
        addItem(new StoneAxe());
        addItem(new StoneShovel());
        addItem(new Furnace());
        addItem(new Rope());
        addItem(new VegetableFiber());
        addItem(new Sapling());
        addItem(new IronIngot());
        addItem(new Campfire());
        addItem(new Hammer());
        addItem(new Handsaw());
        addItem(new Planks());
        addItem(new IronPlate());
        addItem(new Torch());
    }
}