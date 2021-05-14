package com.kgc.sauw.environment.items;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.InventoryContainer;
import com.kgc.sauw.config.ItemConfiguration;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Item {
    public int id;

    public Texture t;
    protected ItemConfiguration ItemConfiguration;

    public Item(int id) {
        ItemConfiguration = new ItemConfiguration(id);
        this.id = id;
    }

    public ItemConfiguration getItemConfiguration() {
        return ItemConfiguration;
    }

    public void onClick(Tile tile) {
    }

    public Texture getTexture(InventoryContainer container) {
        if (t != null)
            return t;
        else return TEXTURES.undef;
    }

    public Texture getDefaultTexture() {
        if (t != null)
            return t;
        else return TEXTURES.undef;
    }

    public String getName(InventoryContainer container) {
        if (container == null)
            return ItemConfiguration.name;
        else return container.DisplayParameters.name;
    }

    public String getDefaultName() {
        return ItemConfiguration.name;
    }
}
