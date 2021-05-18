package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class StoneAxe extends InstrumentItem {
    public StoneAxe() {
        super(ID.registeredId("item:stone_axe"), 128, Type.AXE);

        setTexture(TEXTURES.stone_axe);
        ItemConfiguration.weight = 5.75f;
        ItemConfiguration.name = LANGUAGES.getString("stone_axe");

    }
}
