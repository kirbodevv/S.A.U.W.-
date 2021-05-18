package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class StonePickaxe extends InstrumentItem {
    public StonePickaxe() {
        super(ID.registeredId("item:stone_pickaxe"), 128, Type.PICKAXE);

        setTexture(TEXTURES.stone_pickaxe);

        ItemConfiguration.name = LANGUAGES.getString("stone_pickaxe");
        ItemConfiguration.weight = 5.75f;
    }
}
