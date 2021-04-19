package com.kgc.sauw.environment.items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class StonePickaxe extends InstrumentItem {
    public StonePickaxe() {
        super(14, 128, Type.PICKAXE);

        this.t = TEXTURES.stone_pickaxe;

        ItemConfiguration.name = LANGUAGES.getString("stone_pickaxe");
        ItemConfiguration.weight = 5.75f;
    }
}
