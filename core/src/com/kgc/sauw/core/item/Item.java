package com.kgc.sauw.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.map.Tile;

import static com.kgc.sauw.core.graphic.Graphic.TEXTURES;

public class Item {
    public int id;

    private Texture texture;
    private TextureRegion textureRegion;

    protected ItemConfiguration itemConfiguration;

    public Item(int id) {
        itemConfiguration = new ItemConfiguration(id);
        this.id = id;
    }

    public ItemConfiguration getItemConfiguration() {
        return itemConfiguration;
    }

    public void onClick(Tile tile) {
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
    }

    public TextureRegion getTexture(Container container) {
        if (textureRegion != null)
            return textureRegion;
        else return TEXTURES.undefRegion;
    }

    public Texture getDefaultTexture() {
        if (textureRegion != null)
            return texture;
        else return TEXTURES.undef;
    }

    public String getName(Container container) {
        if (container == null)
            return itemConfiguration.name;
        else return container.DisplayParameters.name;
    }

    public String getDefaultName() {
        return itemConfiguration.name;
    }
}
