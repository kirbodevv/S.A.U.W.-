package com.kgc.sauw.game.items;

import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class StoneAxe extends InstrumentItem {
    public StoneAxe() {
        super(ID.registeredId("item:stone_axe"), 128, Type.AXE);

        setTexture(TEXTURES.stone_axe);
        itemConfiguration.weight = 5.75f;
        itemConfiguration.name = LANGUAGES.getString("stone_axe");

    }
}
