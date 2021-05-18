package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Hammer extends Item {
    public Hammer() {
        super(ID.registeredId("item:hammer"));
        setTexture(TEXTURES.hammer);

        ItemConfiguration.name = LANGUAGES.getString("hammer");
        ItemConfiguration.weight = 1.25f;
    }
}
