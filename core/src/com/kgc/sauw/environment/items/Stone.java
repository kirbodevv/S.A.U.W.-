package com.kgc.sauw.environment.items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Stone extends Item {
    public Stone() {
        super(12);

        this.t = TEXTURES.stone_item;

        ItemConfiguration.weight = 0.15f;
        ItemConfiguration.name = LANGUAGES.getString("stone");
    }
}
