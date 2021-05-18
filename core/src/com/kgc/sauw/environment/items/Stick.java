package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Stick extends Item {
    public Stick() {
        super(ID.registeredId("item:stick"));

        setTexture(TEXTURES.stick);
    }
}
