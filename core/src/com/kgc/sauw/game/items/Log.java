package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class Log extends Item {
    public Log() {
        super(ID.registeredId("item:log"));

        setTexture(TEXTURES.log);

        itemConfiguration.name = LANGUAGES.getString("log");
        itemConfiguration.weight = 1.25f;
    }
}
