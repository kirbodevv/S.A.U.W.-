package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class IronOre extends Item {
    public IronOre() {
        super(ID.registeredId("item:iron_ore"));

        this.t = TEXTURES.iron_ore_item;

        ItemConfiguration.name = LANGUAGES.getString("iron_ore");
        ItemConfiguration.weight = 0.5f;

    }
}
