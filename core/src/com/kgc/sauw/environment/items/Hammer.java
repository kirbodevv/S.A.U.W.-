package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Hammer extends Item {
    public Hammer() {
        super(ID.registeredId("item:hammer"));
        setTexture(TEXTURES.hammer);

        itemConfiguration.name = LANGUAGES.getString("hammer");
        itemConfiguration.maxData = 100;
        itemConfiguration.weight = 1.25f;
    }
}
