package com.kgc.sauw.game.items;

import com.kgc.sauw.core.utils.ID;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.core.utils.Languages.LANGUAGES;

public class StonePickaxe extends InstrumentItem {
    public StonePickaxe() {
        super(ID.registeredId("item:stone_pickaxe"), 128, Type.PICKAXE);

        setTexture(TEXTURES.stone_pickaxe);

        itemConfiguration.name = LANGUAGES.getString("stone_pickaxe");
        itemConfiguration.weight = 5.75f;
    }
}
