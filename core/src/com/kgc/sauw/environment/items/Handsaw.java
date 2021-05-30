package com.kgc.sauw.environment.items;

import com.kgc.sauw.utils.ID;
import com.kgc.sauw.utils.Languages;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Handsaw extends Item {
    public Handsaw() {
        super(ID.registeredId("item:handsaw"));

        setTexture(TEXTURES.handsaw);

        itemConfiguration.weight = 1f;
        itemConfiguration.maxData = 100;
        itemConfiguration.name = Languages.LANGUAGES.getString("handsaw");
    }
}
