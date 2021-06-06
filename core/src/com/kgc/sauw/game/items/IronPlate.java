package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class IronPlate extends Item {
    public IronPlate() {
        super(ID.registeredId("item:iron_plate"));

        setTexture(TEXTURES.iron_plate);

        itemConfiguration.name = LANGUAGES.getString("iron_plate");
        itemConfiguration.weight = 0.8f;
    }
}