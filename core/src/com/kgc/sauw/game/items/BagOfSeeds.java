package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.registry.RegistryMetadata;
import com.kgc.sauw.core.resource.Resource;

@RegistryMetadata(package_ = "sauw", id = "bag_of_seeds")
public class BagOfSeeds extends Item {
    public BagOfSeeds() {
        setTexture(Resource.getTexture("placeholder.png"));
        itemConfiguration.creativeCategory = "sauw:items";
    }
}
