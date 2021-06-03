package com.kgc.sauw.game.items;

import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class StoneShovel extends InstrumentItem {
    public StoneShovel() {
        super(ID.registeredId("item:stone_shovel"), 128, Type.SHOVEL);

        setTexture(TEXTURES.stone_shovel);

        itemConfiguration.name = LANGUAGES.getString("stone_shovel");
        itemConfiguration.weight = 5.75f;
    }
}