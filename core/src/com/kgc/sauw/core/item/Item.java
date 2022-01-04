package com.kgc.sauw.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.world.Tile;
import com.kgc.sauw.core.registry.RegistryObject;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.utils.languages.Languages;

public class Item extends RegistryObject {
    public int id;
    public String name;

    private static final TextureRegion undefRegion;

    static {
        undefRegion = new TextureRegion(Resource.getTexture("blocks/undefined.png"));
    }

    protected Sprite sprite = new Sprite();

    protected ItemConfiguration itemConfiguration;

    private ItemFunctions itemFunctions;

    public Item() {
        initItem();
    }

    private void initItem() {
        itemConfiguration = new ItemConfiguration();
    }

    public ItemConfiguration getItemConfiguration() {
        return itemConfiguration;
    }

    public void setItemFunctions(ItemFunctions itemFunctions) {
        this.itemFunctions = itemFunctions;
    }

    public void setItemConfiguration(ItemConfiguration itemConfiguration) {
        this.itemConfiguration = itemConfiguration;
    }

    public void onClick(Tile tile) {
        if (itemFunctions != null) itemFunctions.onClick(tile);
    }

    public void tick() {
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
}
