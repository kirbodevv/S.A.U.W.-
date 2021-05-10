package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;
import com.kgc.sauw.utils.Languages;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Handsaw extends Item {
    public Handsaw() {
        super(ID.registeredId("item:handsaw"));

        t = TEXTURES.handsaw;

        ItemConfiguration.weight = 1f;
        ItemConfiguration.name = Languages.LANGUAGES.getString("handsaw");
    }
}
