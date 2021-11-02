package com.kgc.sauw.game.generated;

import com.kgc.sauw.core.environment.block.Block;
import com.kgc.sauw.core.environment.block.BlockConfiguration;
import com.kgc.sauw.core.environment.item.*;
import com.kgc.sauw.core.utils.StringUtils;

public class ItemsGenerated {
    public static void init() {
		com.kgc.sauw.core.environment.item.Items.defineItem(new VoidItem());
        ItemConfiguration itemConfiguration;
        Item item;
        

		item = new Item("item:grass");
		itemConfiguration = new ItemConfiguration("item:grass");
		item.setTexture("Blocks/grass_1.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:grass";
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:apple");
		itemConfiguration = new ItemConfiguration("item:apple");
		item.setTexture("Items/apple.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.2f;
		itemConfiguration.type = Type.FOOD;
		itemConfiguration.foodScore = 4;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:chest");
		itemConfiguration = new ItemConfiguration("item:chest");
		item.setTexture("Blocks/chest.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:chest";
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:stick");
		itemConfiguration = new ItemConfiguration("item:stick");
		item.setTexture("Items/stick.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:log");
		itemConfiguration = new ItemConfiguration("item:log");
		item.setTexture("Items/log.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.25f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:stone");
		itemConfiguration = new ItemConfiguration("item:stone");
		item.setTexture("Items/stone.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.15f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:iron_ore");
		itemConfiguration = new ItemConfiguration("item:iron_ore");
		item.setTexture("Items/iron_ore.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.5f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new InstrumentItem("item:stone_pickaxe");
		itemConfiguration = new ItemConfiguration("item:stone_pickaxe");
		item.setTexture("Items/stone_pickaxe.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.AXE;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new InstrumentItem("item:stone_axe");
		itemConfiguration = new ItemConfiguration("item:stone_axe");
		item.setTexture("Items/stone_axe.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.AXE;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new InstrumentItem("item:stone_shovel");
		itemConfiguration = new ItemConfiguration("item:stone_shovel");
		item.setTexture("Items/stone_shovel.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 5.75f;
		itemConfiguration.type = Type.INSTRUMENT;
		itemConfiguration.instrumentType = InstrumentItem.Type.SHOVEL;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:furnace");
		itemConfiguration = new ItemConfiguration("item:furnace");
		item.setTexture("Blocks/furnace.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.01f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:furnace";
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:rope");
		itemConfiguration = new ItemConfiguration("item:rope");
		item.setTexture("Items/rope.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.15f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:vegetable_fiber");
		itemConfiguration = new ItemConfiguration("item:vegetable_fiber");
		item.setTexture("Items/vegetable_fiber.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.05f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:sapling");
		itemConfiguration = new ItemConfiguration("item:sapling");
		item.setTexture("Items/sapling.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.1f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:sapling";
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:iron_ingot");
		itemConfiguration = new ItemConfiguration("item:iron_ingot");
		item.setTexture("Items/iron_ingot.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:campfire");
		itemConfiguration = new ItemConfiguration("item:campfire");
		item.setTexture("Blocks/campfire.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.BLOCK_ITEM;
		itemConfiguration.stringBlockId = "block:campfire";
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:hammer");
		itemConfiguration = new ItemConfiguration("item:hammer");
		item.setTexture("Items/hammer.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 1.25f;
		itemConfiguration.type = Type.ITEM;
		itemConfiguration.maxDamage = 100;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:handsaw");
		itemConfiguration = new ItemConfiguration("item:handsaw");
		item.setTexture("Items/handsaw.png");
		itemConfiguration.maxCount = 1;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		itemConfiguration.maxDamage = 100;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:planks");
		itemConfiguration = new ItemConfiguration("item:planks");
		item.setTexture("Items/planks.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.55f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:iron_plate");
		itemConfiguration = new ItemConfiguration("item:iron_plate");
		item.setTexture("Items/iron_plate.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.8f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:aluminium_ingot");
		itemConfiguration = new ItemConfiguration("item:aluminium_ingot");
		item.setTexture("Items/iron_ingot.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 1.0f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);

		item = new Item("item:aluminium_can");
		itemConfiguration = new ItemConfiguration("item:aluminium_can");
		item.setTexture("Items/aluminium_can.png");
		itemConfiguration.maxCount = 64;
		itemConfiguration.weight = 0.5f;
		itemConfiguration.type = Type.ITEM;
		item.setItemConfiguration(itemConfiguration);
		Items.defineItem(item);
    }
}
