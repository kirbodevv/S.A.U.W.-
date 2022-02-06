package com.kgc.sauw.core.item;

import com.badlogic.gdx.graphics.Texture;

public class ItemBuilder {
    private ItemConfiguration configuration = new ItemConfiguration();
    private Texture texture;

    public ItemBuilder withConfiguration(ItemConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public ItemBuilder withType(Type type) {
        configuration.type = type;
        return this;
    }

    public ItemBuilder withBlockID(String blockID) {
        configuration.stringBlockId = blockID;
        return this;
    }

    public ItemBuilder withInstrumentType(InstrumentItem.Type type) {
        configuration.instrumentType = type;
        return this;
    }

    public ItemBuilder withFoodScore(int foodScore) {
        configuration.foodScore = foodScore;
        return this;
    }

    public ItemBuilder withWeight(float weight) {
        configuration.weight = weight;
        return this;
    }

    public ItemBuilder withMaxCount(int maxCount) {
        configuration.maxCount = maxCount;
        return this;
    }

    public ItemBuilder withMaxDamage(int damage) {
        configuration.maxDamage = damage;
        return this;
    }

    public ItemBuilder withCategory(String category) {
        configuration.creativeCategory = category;
        return this;
    }

    public ItemBuilder withTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setTexture(texture);
        item.setItemConfiguration(configuration);
        return item;
    }
}
