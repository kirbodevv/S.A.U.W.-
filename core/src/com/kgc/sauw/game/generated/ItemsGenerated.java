package com.kgc.sauw.game.generated;

import com.kgc.sauw.core.item.*;

public class ItemsGenerated {
    public static void init() {
		Items.registry.register(new VoidItem(), "sauw", "void");
        ItemConfiguration itemConfiguration;
        Item item;
        

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("blocks/grass_1.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:grass";
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "grass");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/apple.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.2f;
		itemConfiguration.type = Type.FOOD;
		itemConfiguration.foodScore = 4;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "apple");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("blocks/chest.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:chest";
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "chest");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/stick.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "stick");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/log.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.25f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "log");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/stone.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.15f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "stone");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/iron_ore.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.5f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "iron_ore");

		item = new InstrumentItem();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/stone_pickaxe.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.PICKAXE;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "stone_pickaxe");

		item = new InstrumentItem();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/stone_axe.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.AXE;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "stone_axe");

		item = new InstrumentItem();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/stone_shovel.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.SHOVEL;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "stone_shovel");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("blocks/furnace.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:furnace";
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "furnace");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/rope.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.15f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "rope");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/vegetable_fiber.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.05f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "vegetable_fiber");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/sapling.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.1f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:sapling";
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "sapling");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/iron_ingot.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "iron_ingot");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("blocks/campfire.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:campfire";
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "campfire");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/hammer.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 1.25f;
		itemConfiguration.type = Type.ITEM;
		itemConfiguration.maxDamage = 100;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "hammer");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/handsaw.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		itemConfiguration.maxDamage = 100;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "handsaw");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/planks.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.55f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "planks");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/iron_plate.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.8f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "iron_plate");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/iron_ingot.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "aluminium_ingot");

		item = new Item();
		itemConfiguration = new ItemConfiguration();
		item.setTexture("items/aluminium_can.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.5f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.registry.register(item, "sauw", "aluminium_can");

		item = new com.kgc.sauw.game.items.Torch();
		Items.registry.register(item, "sauw", "torch");
    }
}
