package com.kgc.sauw.core.environment.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.environment.world.Tile;
import static com.kgc.sauw.core.GameContext.SAUW;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.core.utils.languages.Languages;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Item {
    public int id;
    private String stringId;
    public String name;

    private static final TextureRegion undefRegion;

    static {
        undefRegion = new TextureRegion(Resource.getTexture("Blocks/undefined.png"));
    }

    protected Sprite sprite = new Sprite();

    protected ItemConfiguration itemConfiguration;

    private ItemFunctions itemFunctions;

    public String getStringId() {
        return stringId;
    }

    public Item(int id, String stringId) {
        this.id = SAUW.registeredId(stringId, id);
        this.stringId = stringId;
        initItem();
    }

    public Item(String id) {
        this.id = SAUW.registeredId(id);
        this.stringId = id;
        initItem();
    }

    private void initItem() {
        itemConfiguration = new ItemConfiguration(id);
        name = Languages.getString("sauw.items." + stringId.split(":")[1]);
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
}
