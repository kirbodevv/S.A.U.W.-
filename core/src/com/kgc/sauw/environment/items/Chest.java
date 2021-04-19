package com.kgc.sauw.environment.items;

import com.kgc.sauw.environment.Items;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Chest extends Item {
    public Chest() {
        super(6);

        this.t = TEXTURES.chest;

        ItemConfiguration.type = Items.Type.BLOCKITEM;
        ItemConfiguration.blockId = 5;
    }
}
