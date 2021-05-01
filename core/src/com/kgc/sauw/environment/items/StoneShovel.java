package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class StoneShovel extends InstrumentItem {
    public StoneShovel() {
        super(ID.registeredId("item:stone_shovel"), 128, Type.SHOVEL);

        this.t = TEXTURES.stone_shovel;

        ItemConfiguration.name = LANGUAGES.getString("stone_shovel");
        ItemConfiguration.weight = 5.75f;
    }
}