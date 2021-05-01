package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Stone extends Item {
    public Stone() {
        super(ID.registeredId("item:stone"));

        this.t = TEXTURES.stone_item;

        ItemConfiguration.weight = 0.15f;
        ItemConfiguration.name = LANGUAGES.getString("stone");
    }
}
