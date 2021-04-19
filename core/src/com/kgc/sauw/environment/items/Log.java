package com.kgc.sauw.environment.items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Log extends Item {
    public Log() {
        super(8);

        this.t = TEXTURES.log;

        ItemConfiguration.name = LANGUAGES.getString("log");
        ItemConfiguration.weight = 1.25f;
    }
}
