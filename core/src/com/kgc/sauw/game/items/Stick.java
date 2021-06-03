package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Stick extends Item {
    public Stick() {
        super(ID.registeredId("item:stick"));

        setTexture(TEXTURES.stick);
    }
}
