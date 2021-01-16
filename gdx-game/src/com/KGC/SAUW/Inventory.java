package com.KGC.SAUW;

import com.KGC.SAUW.Camera2D;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;
import com.KGC.SAUW.Player;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.*;
import com.KGC.SAUW.Items;
import com.badlogic.gdx.graphics.Color;
import com.KGC.SAUW.InterfaceAPI.Button;
import com.KGC.SAUW.InterfaceAPI.Interface;
import com.KGC.SAUW.InterfaceAPI.InterfaceEvents;

public class Inventory {
	private int w, h;
	private BitmapFont itemsCount = new BitmapFont(Gdx.files.internal("ttf.fnt"));
	private SpriteBatch b;
	private Camera2D cam;
	private Texture t;
	private Texture t2;
	private Items items;
	private boolean hided = false;
	private boolean isTouched = false;
	private int x, y;
	private Button openButton;
	Interface inventoryInterface;
	public Inventory(SpriteBatch b, Camera2D c, Texture t, Texture t2, Items items, int x, int y, Textures Textures, GameInterface gi, Langs langs) {
		w = c.W;
		h = c.H;
		itemsCount.setColor(Color.BLACK);
		itemsCount.scale(w / 2500);
		this.b = b;
		this.cam = c;
		this.t = t;
		this.t2 = t2;
		this.x = x;
		this.y = y;
		this.items = items;
		openButton = new Button("", x + w / 16 * 8, y, w / 16, w / 16, Textures.extraButton_0, Textures.extraButton_1);
		//"{\"standart\":{\"header\":{\"text\":{\"text\":\"Inventory\"}}, \"isBlockInterface\":false, \"inventory\" : {\"standart\":true}, \"background\" : {\"standart\" : true, \"full\" : false}}, \"elements\" : {}, \"drawing\" : []}"
		inventoryInterface = new Interface(Interface.InterfaceSizes.STANDART, Textures, b, cam, items, gi);
        inventoryInterface.setHeaderText(langs.getString("inventory")).isBlockInterface(false).createInventory();
		openButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					inventoryInterface.open();
				}
		});
		}
	public void init(final Player pl){
		inventoryInterface.setInterfaceEvents(new InterfaceEvents(){
				@Override
				public void initialize() {

				}

				@Override
				public void tick() {

				}

				@Override
				public void onOpen() {

				}

				@Override
				public void renderBefore() {

				}

				@Override
				public void render() {
					Interface.text.drawMultiLine(b, pl.weight + " | " + pl.maxWeight + "Kg", cam.X + Interface.x + Interface.width - w / 16 * 3, cam.Y + Interface.y + Interface.width - w / 16 * 3, w / 16 * 3, BitmapFont.HAlignment.LEFT);
				}
			});
	}
	public void update(Player pl) {
		inventoryInterface.update(pl, cam);
		if (!hided) {
			openButton.update(cam);
			if (Gdx.input.isTouched()) {
				if (Gdx.input.getX() >= x && Gdx.input.getX() <= 8 * (w / 16) + x && Gdx.input.getY() < h && Gdx.input.getY() > h - (w / 16)) {
					isTouched = true;
				} else {
					isTouched = false;
				}
			}
		}
	}
	public void render(Player pl) {
		if (!hided) {
			b.setColor(1, 1, 1, 0.8f);
			b.draw(t, x + cam.X, y + cam.Y, w / 16 * 8, w / 16);
			for (int i = 0; i < 8; i++) {
				if (Gdx.input.getX() > (i * (w / 16)) + x && Gdx.input.getX() < (i  * (w / 16)) + x + (w / 16) && Gdx.input.getY() < h && Gdx.input.getY() > h - (w / 16)) {
					pl.carriedSlot = i;
				}
			}
			openButton.render(b, cam);
			b.setColor(1, 1, 1, 1);
			b.draw(t2, x + cam.X + (pl.carriedSlot * (w / 16)), y + cam.Y, w / 16, w / 16);
			for (int i = 0; i < 8; i++) {
				if (pl.getInventory()[i].id != 0) {
					b.draw(items.getTextureById(pl.Inventory[i].id), x + cam.X + i * (w / 16) + (w / 64) , y + cam.Y + (w / 64), w / 32/*(w / 16) - ((w / 16) / 32 * 10)*/, w / 32);
					itemsCount.draw(b, "" + pl.Inventory[i].count, i * (w / 16) + x + cam.X, y + cam.Y + (w / 16));
				}
			}
		}
		inventoryInterface.render(pl, cam);
	}
	public boolean isHided() {
		return hided;
	}
	public boolean isTouched() {
		return isTouched;
	}
	public void hide(boolean hide) {
		this.hided = hide;
	}
}
