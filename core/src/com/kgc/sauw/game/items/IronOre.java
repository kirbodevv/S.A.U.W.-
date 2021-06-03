package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class IronOre extends Item {
    public IronOre() {
        super(ID.registeredId("item:iron_ore"));

        setTexture(TEXTURES.iron_ore_item);

        itemConfiguration.name = LANGUAGES.getString("iron_ore");
        itemConfiguration.weight = 0.5f;

    }
}
