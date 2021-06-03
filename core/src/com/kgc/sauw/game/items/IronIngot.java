package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class IronIngot extends Item {
    public IronIngot() {
        super(ID.registeredId("item:iron_ingot"));

        setTexture(TEXTURES.iron_ingot);

        itemConfiguration.name = LANGUAGES.getString("iron_ingot");
        itemConfiguration.weight = 1f;

    }
}
