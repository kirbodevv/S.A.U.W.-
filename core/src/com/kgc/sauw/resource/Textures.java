package com.kgc.sauw.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Textures {
    public Texture j_0;
    public Texture j_1;
    public Texture button_0;
    public Texture button_1;
    public Texture grass0;
    public Texture player;
    public Texture selected_slot;
    public Texture undef;
    public TextureRegion undefRegion;
    public Texture stone;
    public Texture chest;
    public Texture health_0;
    public Texture health_1;
    public Texture button_icon_extra;
    public Texture closeButton;
    public Texture inventory;
    public Texture standardBackground;
    public Texture tree;
    public Texture christmas_tree;
    public Texture snow;
    public Texture icon_up;
    public Texture icon_down;
    public Texture icon_left;
    public Texture icon_right;
    public Texture apple;
    public Texture stick;
    public Texture log;
    public Texture button_icon_crafting;
    public Texture stick_1;
    public Texture wood;
    public Texture clown;
    public Texture SAUWCoin;
    public Texture SAUWIcon;
    public Texture stone_1;
    public Texture stone_item;
    public Texture iron_ore;
    public Texture iron_ore_item;
    public Texture stone_pickaxe;
    public Texture stone_axe;
    public Texture button_icon_console;
    public Texture rope;
    public Texture dirt;
    public Texture vegetable_fiber;
    public Texture iron_ingot;
    public Texture sapling_item;
    public Texture sapling;
    public Texture switch_0;
    public Texture switch_1;
    public Texture furnace;
    public Texture furnace_item;
    public Texture logo;
    public Texture campfire;
    public Texture campfire_animation;
    public Texture player_inv;
    public Texture shadow;
    public Texture stone_shovel;
    public Texture flameParticle;
    public Texture smokeParticle_0;
    public Texture smokeParticle_1;
    public Texture water;
    public Texture player1;
    public Texture table;
    public Texture toolWall;
    public Texture toolWallInstruments;
    public Texture hammer;
    public Texture handsaw;
    public Texture glass;
    public Texture planks;
    public Texture iron_plate;

    public void load() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        clown = new Texture(Gdx.files.internal("clown.jpg"));
        SAUWCoin = new Texture(Gdx.files.internal("SAUW_Coin.png"));
        logo = new Texture(Gdx.files.internal("Interface/SAUW_Logo.png"));
        SAUWIcon = new Texture(Gdx.files.internal("SAUW_icon.png"));

        player1 = new Texture(Gdx.files.internal("player_1.png"));

        flameParticle = new Texture(Gdx.files.internal("particle/flame.png"));
        smokeParticle_0 = new Texture(Gdx.files.internal("particle/smoke.png"));
        smokeParticle_1 = new Texture(Gdx.files.internal("particle/smoke_1.png"));

        player = new Texture(Gdx.files.internal("Entity/player.png"));
        player_inv = new Texture(Gdx.files.internal("Entity/player_inv.png"));
        shadow = new Texture(Gdx.files.internal("Entity/shadow.png"));

        undef = new Texture(Gdx.files.internal("Blocks/undefined.png"));
        undefRegion = new TextureRegion(undef);

        stone = new Texture(Gdx.files.internal("Blocks/stone.png"));
        snow = new Texture(Gdx.files.internal("Blocks/snow.png"));
        stone_1 = new Texture(Gdx.files.internal("Blocks/stone_1.png"));
        grass0 = new Texture(Gdx.files.internal("Blocks/grass_1.png"));
        glass = new Texture(Gdx.files.internal("Blocks/glass.png"));
        chest = new Texture(Gdx.files.internal("Blocks/chest.png"));
        tree = new Texture(Gdx.files.internal("Blocks/tree.png"));
        christmas_tree = new Texture(Gdx.files.internal("Blocks/christmas_tree.png"));
        wood = new Texture(Gdx.files.internal("Blocks/wood.png"));
        iron_ore = new Texture(Gdx.files.internal("Blocks/iron_ore.png"));
        dirt = new Texture(Gdx.files.internal("Blocks/dirt.png"));
        sapling = new Texture(Gdx.files.internal("Blocks/sapling.png"));
        furnace = new Texture(Gdx.files.internal("Blocks/furnace.png"));
        campfire = new Texture(Gdx.files.internal("Blocks/campfire.png"));
        water = new Texture(Gdx.files.internal("Blocks/water.png"));
        table = new Texture(Gdx.files.internal("Blocks/table.png"));
        toolWall = new Texture(Gdx.files.internal("Blocks/tool_wall.png"));
        toolWallInstruments = new Texture(Gdx.files.internal("Blocks/tool_wall_instruments.png"));
        campfire_animation = new Texture(Gdx.files.internal("Blocks/campfire_animation.png"));


        j_0 = new Texture(Gdx.files.internal("Interface/Joystick_0.png"));
        j_1 = new Texture(Gdx.files.internal("Interface/Joystick_1.png"));
        button_0 = new Texture(Gdx.files.internal("Interface/button_0.png"));
        button_1 = new Texture(Gdx.files.internal("Interface/button_1.png"));
        selected_slot = new Texture(Gdx.files.internal("Interface/selected_slot.png"));
        health_0 = new Texture(Gdx.files.internal("Interface/health_0.png"));
        health_1 = new Texture(Gdx.files.internal("Interface/health_1.png"));

        closeButton = new Texture(Gdx.files.internal("Interface/closeButton.png"));
        inventory = new Texture(Gdx.files.internal("Interface/inventory.png"));
        standardBackground = TextureGenerator.generateBackground(16, (float) (h / (w / 16)));

        icon_up = new Texture(Gdx.files.internal("Interface/button_up_0.png"));
        icon_down = new Texture(Gdx.files.internal("Interface/button_down_0.png"));
        icon_left = new Texture(Gdx.files.internal("Interface/button_left_0.png"));
        icon_right = new Texture(Gdx.files.internal("Interface/button_right_0.png"));
        button_icon_extra = new Texture(Gdx.files.internal("Interface/extraButton_0.png"));
        button_icon_crafting = new Texture(Gdx.files.internal("Interface/crafting_button_0.png"));
        button_icon_console = new Texture(Gdx.files.internal("Interface/console_button_0.png"));

        switch_0 = new Texture(Gdx.files.internal("Interface/switch_0.png"));
        switch_1 = new Texture(Gdx.files.internal("Interface/switch_1.png"));

        stick = new Texture(Gdx.files.internal("Items/stick.png"));
        stick_1 = new Texture(Gdx.files.internal("Items/stick_1.png"));
        log = new Texture(Gdx.files.internal("Items/log.png"));
        stone_item = new Texture(Gdx.files.internal("Items/stone.png"));
        iron_ore_item = new Texture(Gdx.files.internal("Items/iron_ore.png"));
        stone_pickaxe = new Texture(Gdx.files.internal("Items/stone_pickaxe.png"));
        stone_axe = new Texture(Gdx.files.internal("Items/stone_axe.png"));
        stone_shovel = new Texture(Gdx.files.internal("Items/stone_shovel.png"));
        rope = new Texture(Gdx.files.internal("Items/rope.png"));
        vegetable_fiber = new Texture(Gdx.files.internal("Items/vegetable_fiber.png"));
        iron_ingot = new Texture(Gdx.files.internal("Items/iron_ingot.png"));
        sapling_item = new Texture(Gdx.files.internal("Items/sapling_item.png"));
        furnace_item = new Texture(Gdx.files.internal("Items/furnace_item.png"));
        apple = new Texture(Gdx.files.internal("Items/apple.png"));
        hammer = new Texture(Gdx.files.internal("Items/hammer.png"));
        handsaw = new Texture(Gdx.files.internal("Items/hand_saw.png"));
        planks = new Texture(Gdx.files.internal("Items/planks.png"));
        iron_plate = new Texture(Gdx.files.internal("Items/iron_plate.png"));
    }

    public void dispose() {
        handsaw.dispose();
        stone_shovel.dispose();
        shadow.dispose();
        j_0.dispose();
        j_1.dispose();
        iron_plate.dispose();
        button_0.dispose();
        button_1.dispose();
        grass0.dispose();
        player.dispose();
        selected_slot.dispose();
        undef.dispose();
        stone.dispose();
        chest.dispose();
        health_0.dispose();
        health_1.dispose();
        button_icon_extra.dispose();
        closeButton.dispose();
        inventory.dispose();
        standardBackground.dispose();
        tree.dispose();
        table.dispose();
        planks.dispose();
        icon_up.dispose();
        icon_down.dispose();
        icon_left.dispose();
        icon_right.dispose();
        stick.dispose();
        log.dispose();
        button_icon_crafting.dispose();
        stick_1.dispose();
        wood.dispose();
        clown.dispose();
        SAUWCoin.dispose();
        stone_1.dispose();
        stone_item.dispose();
        iron_ore.dispose();
        iron_ore_item.dispose();
        stone_pickaxe.dispose();
        stone_axe.dispose();
        toolWall.dispose();
        toolWallInstruments.dispose();
        button_icon_console.dispose();
        rope.dispose();
        dirt.dispose();
        vegetable_fiber.dispose();
        iron_ingot.dispose();
        sapling_item.dispose();
        sapling.dispose();
        switch_0.dispose();
        switch_1.dispose();
        furnace.dispose();
        furnace_item.dispose();
        logo.dispose();
        campfire.dispose();
        campfire_animation.dispose();
        snow.dispose();
        christmas_tree.dispose();
        apple.dispose();
        SAUWIcon.dispose();
        flameParticle.dispose();
        smokeParticle_0.dispose();
        smokeParticle_1.dispose();
        water.dispose();
        hammer.dispose();
    }
}