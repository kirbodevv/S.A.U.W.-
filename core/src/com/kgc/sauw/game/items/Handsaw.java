package com.kgc.sauw.game.items;

import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.utils.Languages;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Handsaw extends Item {
    public Handsaw() {
        super(ID.registeredId("item:handsaw"));

        setTexture(TEXTURES.handsaw);

        itemConfiguration.weight = 1f;
        itemConfiguration.maxDamage = 100;
        itemConfiguration.name = Languages.LANGUAGES.getString("handsaw");
    }
}
