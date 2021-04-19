package com.kgc.sauw.environment.items;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.config.ItemConfiguration;
import com.kgc.sauw.map.Tile;

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

    public void onClick(Tile tile){}

}
