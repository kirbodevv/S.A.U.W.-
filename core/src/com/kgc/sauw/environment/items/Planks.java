package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Planks extends Item {

    public Planks() {
        super(ID.registeredId("item:planks"));

        setTexture(TEXTURES.planks);

        itemConfiguration.name = LANGUAGES.getString("planks");
        itemConfiguration.weight = 0.55f;
    }
}
