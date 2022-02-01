package com.kgc.sauw.core.creative_categories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.IntArray;
import com.kgc.sauw.core.item.Item;
import com.kgc.sauw.core.registry.RegistryObject;

public class Category extends RegistryObject {
    private final Texture icon;
    private final IntArray items = new IntArray();

    public Category(Texture icon) {
        this.icon = icon;
    }

    public Texture getIcon() {
        return icon;
    }

    public Category addItem(Item item) {
        items.add(item.getId());
        return this;
    }

    public Category addItems(Item... items) {
        for (Item item : items) addItem(item);
        return this;
    }

    public IntArray getItems() {
        return items;
    }

    @Override
    public void init() {

    }
}
