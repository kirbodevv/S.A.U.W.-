package com.kgc.sauw.core.environment.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.ID;
import com.kgc.sauw.core.environment.world.Tile;

public class Item {
    public int id;

    private static final TextureRegion undefRegion;

    static {
        undefRegion = new TextureRegion(Resource.getTexture("Blocks/undefined.png"));
    }

    private Texture texture;
    private TextureRegion textureRegion;

    protected ItemConfiguration itemConfiguration;

    private ItemFunctions itemFunctions;

    public Item(String id) {
        this(ID.registeredId(id));
    }

    public Item(int id) {
        itemConfiguration = new ItemConfiguration(id);
        this.id = id;
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
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
    }

    public TextureRegion getTextureRegion(Container container) {
        if (textureRegion != null)
            return textureRegion;
        else return undefRegion;
    }

    public String getName(Container container) {
        return itemConfiguration.name;
    }

    public String getDefaultName() {
        return itemConfiguration.name;
    }
}
