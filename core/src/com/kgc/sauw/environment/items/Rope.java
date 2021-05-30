package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Rope extends Item {
    public Rope() {
        super(ID.registeredId("item:rope"));

        setTexture(TEXTURES.rope);

        itemConfiguration.name = LANGUAGES.getString("rope");
    }
}
