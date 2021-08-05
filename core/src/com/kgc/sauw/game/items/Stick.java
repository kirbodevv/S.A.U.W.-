package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.ID;

public class Stick extends Item {
    public Stick() {
        super(ID.registeredId("item:stick"));

        setTexture(Resource.getTexture("Items/stick.png"));
    }
}
