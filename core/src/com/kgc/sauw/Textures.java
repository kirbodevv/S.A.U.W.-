package com.kgc.sauw;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;


public class Textures {
	public Texture j_0;
	public Texture j_1;
	public Texture button_0;
	public Texture button_1;
	public Texture door;
	public Texture grass0;
	public Texture player;
	//public Texture slot;
	public Texture selected_slot;
	public Texture undf;
	public Texture stone;
	public Texture wall0;
	public Texture wall1;
	public Texture wall2;
	public Texture wall3;
	public Texture wall4;
	public Texture wall5;
	public Texture wall6;
	public Texture wall7;
	public Texture wall8;
	public Texture wall9;
	public Texture wall10;
	public Texture chest;
	public Texture health_0;
	public Texture health_1;
	public Texture extraButton_0;
	public Texture extraButton_1;
	public Texture closeButton;
	public Texture inventory;
	public Texture standartBackground;
	public Texture standartBackground_full;
	public Texture tree;
	public Texture christmas_tree;
	public Texture snow;
	public Texture button_up_0;
	public Texture button_up_1;
	public Texture button_down_0;
	public Texture button_down_1;
	public Texture button_left_0;
	public Texture button_left_1;
	public Texture button_right_0;
	public Texture button_right_1;
	public Texture apple;
	public Texture stick;
	public Texture log;
	public Texture crafting_button_0;
	public Texture crafting_button_1;
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
	public Texture console_button_0;
	public Texture console_button_1;
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

	public Textures() {

	}
	public static Texture generateTexture(float w, float h, Color background, Color c1, Color c2, Color c3) {
		Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/wood.png"));
		int blockSize = texture.getWidth();
		texture.dispose();
		int width = (int)(blockSize * w);
		int height = (int)(blockSize * h);
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(background);
		pixmap.fill();
		pixmap.setColor(c1);
		pixmap.drawPixel(0, height - 1);
		pixmap.drawPixel(width - 1, 0);

		pixmap.setColor(c2);
		pixmap.drawLine(0, height - 2, 0, 0);
		pixmap.drawLine(0, 0, width - 2, 0);

		pixmap.setColor(c3);
		pixmap.drawLine(1, height - 1, width - 1, height - 1);
		pixmap.drawLine(width - 1, height - 1, width - 1, 1);
		Texture t = new Texture(pixmap);
		pixmap.dispose();
	    return t;
	}
	private static Pixmap generatePixmap(float w, float h, boolean c) {
		Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/stone.png"));
		Color pixCol = new Color();
        int blockSize = texture.getWidth();
		int width = (int)(blockSize * w);
		int height = (int)(blockSize * h);
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		int x = (int)(Math.floor(w) + ((w - Math.floor(w) > 0) ? 1 : 0));
		int y = (int)(Math.floor(h) + ((h - Math.floor(h) > 0) ? 1 : 0));
		for (int xx = 0; xx < x; xx++) {
			for (int yy = 0; yy < y; yy++) {
				pixmap.drawPixmap(texture, 0, 0, texture.getWidth(), texture.getHeight(), xx * blockSize, yy * blockSize, blockSize, blockSize);
			}
		}
		for (int xx = 0; xx < pixmap.getWidth(); xx++) {
			for (int yy = 0; yy < pixmap.getHeight(); yy++) {
				pixCol.set(pixmap.getPixel(xx, yy));
				if ((xx == 0 && yy == pixmap.getHeight() - 1) || (xx == pixmap.getWidth() - 1 && yy == 0)) {
					pixmap.setColor(pixCol.r * 0.7f, pixCol.g * 0.7f, pixCol.b * 0.7f, 1);
				} else if ((xx == 0) || (yy == 0)) {
					if (c)
						pixmap.setColor(pixCol.r, pixCol.g, pixCol.b, 1);
					else
						pixmap.setColor(pixCol.r * 0.4f, pixCol.g * 0.4f, pixCol.b * 0.4f, 1);
				} else if ((xx == pixmap.getWidth() - 1) || (yy == pixmap.getHeight() - 1)) {
					if (c)
						pixmap.setColor(pixCol.r * 0.4f, pixCol.g * 0.4f, pixCol.b * 0.4f, 1);
					else 
						pixmap.setColor(pixCol.r, pixCol.g, pixCol.b, 1);
				} else {
					if (c)
						pixmap.setColor(pixCol.r * 0.8f, pixCol.g * 0.8f, pixCol.b * 0.8f, 1);
					else
						pixmap.setColor(pixCol.r * 0.6f, pixCol.g * 0.6f, pixCol.b * 0.6f, 1);
				}
				pixmap.drawPixel(xx, yy);
			}
		}
		texture.dispose();
	    return pixmap;
	}
	public static Texture generateTexture(float w, float h, boolean c) {
		Pixmap p = generatePixmap(w, h, c);
		Texture t = new Texture(p);
		p.dispose();
		return t;
	}
	public static Texture generateTexture(float w, float h, boolean c, String iconPath) {
		Pixmap p = generatePixmap(w, h, c);
		Pixmap icon = new Pixmap(Gdx.files.internal(iconPath));
		p.setBlending(Pixmap.Blending.SourceOver);
		p.drawPixmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), 0, 0, p.getWidth(), p.getHeight());
		Texture t = new Texture(p);
		p.dispose();
		icon.dispose();
		return t;
	} 
	public static Texture generateBackground(float w, float h) {
		Pixmap texture = new Pixmap(Gdx.files.internal("Blocks/wood.png"));
		int blockSize = texture.getWidth();
		int width = (int)(blockSize * w);
		int height = (int)(blockSize * h);
		Gdx.app.log("genW", width + "");
		Gdx.app.log("genH", height + "");
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		//pixmap.setColor(204f / 255, 204f / 255, 204f / 255, 1);
		pixmap.setBlending(Pixmap.Blending.None);
		//pixmap.fill();

		int x = (int)(Math.floor(w) + ((w - Math.floor(w) > 0) ? 1 : 0));
		int y = (int)(Math.floor(h) + ((h - Math.floor(h) > 0) ? 1 : 0));
		for (int xx = 0; xx < x; xx++) {
			for (int yy = 0; yy < y; yy++) {
				pixmap.drawPixmap(texture, 0, 0, texture.getWidth(), texture.getHeight(), xx * blockSize, yy * blockSize, blockSize, blockSize);
			}
		}
		pixmap.setColor(0, 0, 0, 1);
		pixmap.drawRectangle(0, 0, width, height);
		pixmap.setColor(0, 0, 0, 0);
		pixmap.fillRectangle(0, 0, 3, 3);
		pixmap.fillRectangle(0, height - 3, 3, 3);
		pixmap.fillRectangle(width - 3, 0, 3, 3);
		pixmap.fillRectangle(width - 3, height - 3, 3, 3);
		pixmap.setColor(0, 0, 0, 1);
		pixmap.drawPixel(1, 2);
		pixmap.drawPixel(2, 1);
		pixmap.drawPixel(width - 2, 2);
		pixmap.drawPixel(width - 3, 1);
		pixmap.drawPixel(1, height - 3);
		pixmap.drawPixel(2, height - 2);
		pixmap.drawPixel(width - 3, height - 2);
		pixmap.drawPixel(width - 2, height - 3);

		Texture t = new Texture(pixmap);
		pixmap.dispose();
		texture.dispose();
		return t;

	}
	public void load() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();

		clown    = new Texture(Gdx.files.internal("clown.jpg"));
		SAUWCoin = new Texture(Gdx.files.internal("SAUW_Coin.png"));
		logo     = new Texture(Gdx.files.internal("Interface/SAUW_Logo.png"));
		SAUWIcon = new Texture(Gdx.files.internal("SAUW_icon.png"));
		
		player     = new Texture(Gdx.files.internal("Entity/player.png"));
		player_inv = new Texture(Gdx.files.internal("Entity/player_inv.png"));
		shadow     = new Texture(Gdx.files.internal("Entity/shadow.png"));

		wall0  = new Texture(Gdx.files.internal("Blocks/walls/wall_0.png"));
		wall1  = new Texture(Gdx.files.internal("Blocks/walls/wall_1.png"));
		wall2  = new Texture(Gdx.files.internal("Blocks/walls/wall_2.png"));
		wall3  = new Texture(Gdx.files.internal("Blocks/walls/wall_3.png"));
		wall4  = new Texture(Gdx.files.internal("Blocks/walls/wall_4.png"));
		wall5  = new Texture(Gdx.files.internal("Blocks/walls/wall_5.png"));
		wall6  = new Texture(Gdx.files.internal("Blocks/walls/wall_6.png"));
		wall7  = new Texture(Gdx.files.internal("Blocks/walls/wall_7.png"));
		wall8  = new Texture(Gdx.files.internal("Blocks/walls/wall_8.png"));
		wall9  = new Texture(Gdx.files.internal("Blocks/walls/wall_9.png"));
		wall10 = new Texture(Gdx.files.internal("Blocks/walls/wall_10.png"));

		undf       = new Texture(Gdx.files.internal("Blocks/undefined.png"));
		stone      = new Texture(Gdx.files.internal("Blocks/stone.png"));
		snow       = new Texture(Gdx.files.internal("Blocks/snow.png"));
		stone_1    = new Texture(Gdx.files.internal("Blocks/stone_1.png"));
		grass0     = new Texture(Gdx.files.internal("Blocks/grass_1.png"));
		door       = new Texture(Gdx.files.internal("Blocks/door_0.png"));
		chest      = new Texture(Gdx.files.internal("Blocks/chest.png"));
		tree       = new Texture(Gdx.files.internal("Blocks/tree.png"));
		christmas_tree = new Texture(Gdx.files.internal("Blocks/christmas_tree.png"));
		wood       = new Texture(Gdx.files.internal("Blocks/wood.png"));
		iron_ore   = new Texture(Gdx.files.internal("Blocks/iron_ore.png"));
		dirt       = new Texture(Gdx.files.internal("Blocks/dirt.png"));
		sapling    = new Texture(Gdx.files.internal("Blocks/sapling.png"));
		furnace    = new Texture(Gdx.files.internal("Blocks/furnace.png"));
		campfire   = new Texture(Gdx.files.internal("Blocks/campfire.png"));
		campfire_animation = new Texture(Gdx.files.internal("Blocks/campfire_animation.png"));


		j_0                     = new Texture(Gdx.files.internal("Interface/Joystick_0.png"));
		j_1                     = new Texture(Gdx.files.internal("Interface/Joystick_1.png"));
		button_0                = new Texture(Gdx.files.internal("Interface/button_0.png"));
		button_1                = new Texture(Gdx.files.internal("Interface/button_1.png"));
		//slot                    = new Texture(Gdx.files.internal("Interface/slot.png"));
		selected_slot           = new Texture(Gdx.files.internal("Interface/selected_slot.png"));
		health_0                = new Texture(Gdx.files.internal("Interface/health_0.png"));
		health_1                = new Texture(Gdx.files.internal("Interface/health_1.png"));
		extraButton_0           = generateTexture(1, 1, true, "Interface/extraButton_0.png");
		extraButton_1           = generateTexture(1, 1, false, "Interface/extraButton_0.png");
	    closeButton             = new Texture(Gdx.files.internal("Interface/closeButton.png"));
		inventory               = new Texture(Gdx.files.internal("Interface/inventory.png"));
		standartBackground      = generateBackground(8, 8);
		standartBackground_full = generateBackground(16, (float)(h / (w / 16)));// = new Texture(Gdx.files.internal("Interface/standart_background_full.png"));
		button_up_0             = generateTexture(1, 1, true, "Interface/button_up_0.png");
		button_up_1             = generateTexture(1, 1, false, "Interface/button_up_0.png");
		button_down_0           = generateTexture(1, 1, true, "Interface/button_down_0.png");
		button_down_1           = generateTexture(1, 1, false, "Interface/button_down_0.png");
		button_left_0           = generateTexture(1, 1, true, "Interface/button_left_0.png");
		button_left_1           = generateTexture(1, 1, false, "Interface/button_left_0.png");
		button_right_0          = generateTexture(1, 1, true, "Interface/button_right_0.png");
		button_right_1          = generateTexture(1, 1, false, "Interface/button_right_0.png");
		/*button1_0               = new Texture(Gdx.files.internal("Interface/button1_0.png"));
		 button1_1               = new Texture(Gdx.files.internal("Interface/button1_1.png"));*/
		//log_intr                = new Texture(Gdx.files.internal("Interface/log_intr.png"));
		crafting_button_0       = generateTexture(1, 1, true, "Interface/crafting_button_0.png");
		crafting_button_1       = generateTexture(1, 1, false, "Interface/crafting_button_0.png");
		//input_background        = new Texture(Gdx.files.internal("Interface/input_background.png"));
		console_button_0        = generateTexture(1, 1, true, "Interface/console_button_0.png");
		console_button_1        = generateTexture(1, 1, false, "Interface/console_button_0.png");
        switch_0                = new Texture(Gdx.files.internal("Interface/switch_0.png"));
		switch_1                = new Texture(Gdx.files.internal("Interface/switch_1.png"));

		stick           = new Texture(Gdx.files.internal("Items/stick.png")); 
		stick_1         = new Texture(Gdx.files.internal("Items/stick_1.png"));
		log             = new Texture(Gdx.files.internal("Items/log.png"));
		stone_item      = new Texture(Gdx.files.internal("Items/stone.png"));
		iron_ore_item   = new Texture(Gdx.files.internal("Items/iron_ore.png"));
		stone_pickaxe   = new Texture(Gdx.files.internal("Items/stone_pickaxe.png"));
		stone_axe       = new Texture(Gdx.files.internal("Items/stone_axe.png"));
		stone_shovel    = new Texture(Gdx.files.internal("Items/stone_shovel.png"));
	    rope            = new Texture(Gdx.files.internal("Items/rope.png"));
		vegetable_fiber = new Texture(Gdx.files.internal("Items/vegetable_fiber.png"));
	    iron_ingot      = new Texture(Gdx.files.internal("Items/iron_ingot.png"));
	    sapling_item    = new Texture(Gdx.files.internal("Items/sapling_item.png"));
	    furnace_item    = new Texture(Gdx.files.internal("Items/furnace_item.png"));
		apple           = new Texture(Gdx.files.internal("Items/apple.png"));
	}
	public void dispose() {
		stone_shovel.dispose();
		shadow.dispose();
		j_0.dispose();
		j_1.dispose();
		button_0.dispose();
		button_1.dispose();
		door.dispose();
		grass0.dispose();
		player.dispose();
		//slot.dispose();
		selected_slot.dispose();
		undf.dispose();
		stone.dispose();
		wall0.dispose();
		wall1.dispose();
		wall2.dispose();
		wall3.dispose();
		wall4.dispose();
		wall5.dispose();
		wall6.dispose();
		wall7.dispose();
		wall8.dispose();
		wall9.dispose();
		wall10.dispose();
		chest.dispose();
		health_0.dispose();
		health_1.dispose();
		extraButton_0.dispose();
		extraButton_1.dispose();
		closeButton.dispose();
		inventory.dispose();
		standartBackground.dispose();
		standartBackground_full.dispose();
		tree.dispose();
		button_up_0.dispose();
		button_up_1.dispose();
		button_down_0.dispose();
		button_down_1.dispose();
		button_left_0.dispose();
		button_left_1.dispose();
		button_right_0.dispose();
		button_right_1.dispose();
		/*button1_0.dispose();
		 button1_1.dispose();*/
		//log_intr.dispose();
		stick.dispose();
		log.dispose();
		crafting_button_0.dispose();
		crafting_button_1.dispose();
		stick_1.dispose();
		wood.dispose();
		//Notification_background.dispose();
		clown.dispose();
		SAUWCoin.dispose();
		stone_1.dispose();
		stone_item.dispose();
		iron_ore.dispose();
		iron_ore_item.dispose();
		stone_pickaxe.dispose();
		stone_axe.dispose();
		//input_background.dispose();
		console_button_0.dispose();
		console_button_1.dispose();
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
	}
}
