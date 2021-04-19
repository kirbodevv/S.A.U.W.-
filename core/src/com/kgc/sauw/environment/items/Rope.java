package com.kgc.sauw.environment.items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Rope extends Item {
    public Rope() {
        super(17);

        this.t = TEXTURES.rope;

        ItemConfiguration.name = LANGUAGES.getString("rope");
    }
}
