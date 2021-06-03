package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Hammer extends Item {
    public Hammer() {
        super(ID.registeredId("item:hammer"));
        setTexture(TEXTURES.hammer);

        itemConfiguration.name = LANGUAGES.getString("hammer");
        itemConfiguration.maxDamage = 100;
        itemConfiguration.weight = 1.25f;
    }
}
