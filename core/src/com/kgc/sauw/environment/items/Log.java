package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Log extends Item {
    public Log() {
        super(ID.registeredId("item:log"));

        setTexture(TEXTURES.log);

        ItemConfiguration.name = LANGUAGES.getString("log");
        ItemConfiguration.weight = 1.25f;
    }
}
