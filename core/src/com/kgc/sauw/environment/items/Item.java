package com.kgc.sauw.environment.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.InventoryContainer;
import com.kgc.sauw.config.ItemConfiguration;
import com.kgc.sauw.map.Tile;

import static com.kgc.sauw.graphic.Graphic.TEXTURES;

public class Item {
    public int id;

    private Texture texture;
    private TextureRegion textureRegion;

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

    protected void setTexture(Texture texture) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
    }

    public TextureRegion getTexture(InventoryContainer container) {
        if (textureRegion != null)
            return textureRegion;
        else return TEXTURES.undefRegion;
    }

    public Texture getDefaultTexture() {
        if (textureRegion != null)
            return texture;
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
