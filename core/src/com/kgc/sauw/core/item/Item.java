package com.kgc.sauw.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.registry.RegistryObject;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.languages.Languages;
import com.kgc.sauw.core.world.Tile;

public class Item extends RegistryObject {
    public String name;
    protected Sprite sprite = new Sprite();
    protected ItemConfiguration itemConfiguration;

    public Item() {
        itemConfiguration = new ItemConfiguration();
    }

    public ItemConfiguration getItemConfiguration() {
        return itemConfiguration;
    }

    public void setItemConfiguration(ItemConfiguration itemConfiguration) {
        this.itemConfiguration = itemConfiguration;
    }

    public void setTexture(String path) {
        setTexture(Resource.getTexture(path));
    }

    public void setTexture(Texture texture) {
        sprite.setRegion(texture);
    }

    public Sprite getTexture(Container container) {
        return sprite;
    }

    public String getName(Container container) {
        return name;
    }

    public String getDefaultName() {
        return name;
    }

    @Override
    public void init() {
        name = Languages.getString(package_ + ".items." + stringId);
    }

    public void onClick(Tile tile) {
    }

    public void tick() {
    }
}
