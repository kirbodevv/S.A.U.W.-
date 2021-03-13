package com.kgc.sauw;

import com.badlogic.gdx.Application;
import com.kgc.sauw.Camera2D;
import com.kgc.sauw.InterfaceAPI.Button;
import com.kgc.sauw.InterfaceAPI.Interface;
import com.kgc.sauw.InterfaceAPI.InterfaceEvents;
import com.kgc.sauw.InterfaceAPI.Slot;
import com.kgc.sauw.Items;
import com.kgc.sauw.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.text.DecimalFormat;

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
	TextureRegion[] playerAnimFrames;
	TextureRegion currentFrame;
	private float stateTime = 0.0f;
	private float timer = 0.0f;

	Animation playerAnim;
	Animation tiredPlayerAnim;

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
		inventoryInterface = new Interface(Interface.InterfaceSizes.FULL, Textures, b, cam, items, gi);
        inventoryInterface.setHeaderText(langs.getString("inventory")).isBlockInterface(false).createInventory();
		openButton.setEventListener(new Button.EventListener(){
				@Override
				public void onClick() {
					inventoryInterface.open();
				}
			});
		TextureRegion[][] tmp = TextureRegion.split(Textures.player_inv, Textures.player_inv.getWidth() / 3, Textures.player_inv.getHeight());
		playerAnimFrames = new TextureRegion[3];
		int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                playerAnimFrames[index++] = tmp[i][j];
            }
        }
		playerAnim = new Animation(0.2f, playerAnimFrames[0], playerAnimFrames[1], playerAnimFrames[2], playerAnimFrames[1], playerAnimFrames[0]);
		tiredPlayerAnim = new Animation(0.2f, playerAnimFrames[1], playerAnimFrames[2], playerAnimFrames[2], playerAnimFrames[2], playerAnimFrames[1]);

		currentFrame = playerAnimFrames[0];
	}
	public void init(final Player pl, final Textures textures, final Langs langs) {

		inventoryInterface.setInterfaceEvents(new InterfaceEvents(){
				int plW = w / 24 * 4 * 10 / 26;
				int plH = w / 24 * 4;
				Texture background0;
				Texture background1;
				Slot[] hotbarslots = new Slot[8];
				DecimalFormat DF = new DecimalFormat("#.##");
				@Override
				public void initialize() {
					background0 = Textures.generateTexture(6f, 0.25f, true);
					background1 = Textures.generateTexture(2f, 2f, true);
					int slotW = w / 32 * 11 / 8;
					for (int i = 0; i < 8; i++) {
						final int ii = i;
						final Slot s = new Slot("hotbarslot_" + i, w / 16 * 9 + w / 64 + slotW * i, w / 32 * 7 - w / 64, slotW, slotW, textures.selected_slot);
						s.setSF(new Slot.SlotFunctions(){
								@Override
								public void onClick() {
									pl.hotbar[ii] = -1;
								}
								@Override
								public boolean isValid(int id, int count, int data, String FromSlotWithId) {
									if (FromSlotWithId.contains("InventorySlot_")) {
										for (int i = 0; i < pl.hotbar.length; i++) {
											if (pl.hotbar[i] == Interface.currentTabInv * 30 + Integer.parseInt(FromSlotWithId.substring(14))) {
												pl.hotbar[i] = -1;
											}
										}
										pl.hotbar[ii] = Interface.currentTabInv * 30 + Integer.parseInt(FromSlotWithId.substring(14));
									}

									return false;
								}
							});
						hotbarslots[i] = s;
						this.Interface.slots.add(s);
					}
				}

				@Override
				public void tick() {
					for (int i = 0; i < hotbarslots.length; i++) {
						if (pl.hotbar[i] != -1) {
							hotbarslots[i].id = pl.getItemFromHotbar(i).id;
							hotbarslots[i].count = pl.Inventory.get(pl.hotbar[i]).count;
						}
					}
					timer += Gdx.graphics.getRawDeltaTime();
					if (timer >= 6) {
						stateTime += Gdx.graphics.getDeltaTime();
						if (pl.weight < pl.maxWeight) {
							currentFrame = playerAnim.getKeyFrame(stateTime, true);
							if (playerAnim.getKeyFrameIndex(stateTime) == 4) {
								timer = 0;
								stateTime = 0;
								currentFrame = playerAnimFrames[0];
							}
						} else {
							currentFrame = tiredPlayerAnim.getKeyFrame(stateTime, true);
							if (tiredPlayerAnim.getKeyFrameIndex(stateTime) == 4) {
								timer = 0;
								stateTime = 0;
								currentFrame = playerAnimFrames[1];
							}
						}

					}
				}

				@Override
				public void onOpen() {

				}

				@Override
				public void renderBefore() {

				}

				@Override
				public void render() {
					Interface.text.drawMultiLine(b, DF.format(pl.weight) + " | " + DF.format(pl.maxWeight) + "Kg", w / 16 * 9 + w / 64, w / 32 * 9, w / 16 * 3, BitmapFont.HAlignment.LEFT);
					Interface.text.drawMultiLine(b, langs.getString("backpack"), Interface.previosTabInv.X + Interface.previosTabInv.width, Interface.previosTabInv.Y + Interface.previosTabInv.height - ((Interface.previosTabInv.height - Interface.text.getCapHeight()) / 2), Interface.nextTabInv.X - (Interface.previosTabInv.X + Interface.previosTabInv.width), BitmapFont.HAlignment.CENTER);
					b.draw(currentFrame, w / 16 * 12 - plW / 2, w / 16 * 4, plW, plH);
					b.draw(background0, w / 16 * 9, w / 16 * 3 - w / 64, w / 16 * 6, w / 64);
					if (Interface.currentItemInv != -1 && Interface.currentItemInv < pl.Inventory.size()) {
						b.draw(background1, w / 16 * 9 + w / 128, w / 32 + w / 128, w / 16 * 2, w / 16 * 2);
						b.draw(items.getTextureById(pl.Inventory.get(Interface.currentItemInv).id), w / 16 * 9 + w / 128, w / 32 + w / 128, w / 16 * 2, w / 16 * 2);
						Interface.text.drawMultiLine(b, items.getItemById(pl.Inventory.get(Interface.currentItemInv).id).weight + " Kg", w / 16 * 11 + w / 64, w / 32 * 4, w / 16 * 4, BitmapFont.HAlignment.LEFT);
						Interface.text.drawMultiLine(b, items.getNameById(pl.Inventory.get(Interface.currentItemInv).id), w / 16 * 11 + w / 64, w / 32 * 5, w / 16 * 4, BitmapFont.HAlignment.LEFT);
					}
				}
			});
	}
	public void update(Player pl) {
		inventoryInterface.update(pl, cam);
		if(Gdx.app.getType() == Application.ApplicationType.Desktop){
			openButton.hide(true);
		}
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
				if (pl.hotbar[i] != -1) {
					b.draw(items.getTextureById(pl.getItemFromHotbar(i).id), x + cam.X + i * (w / 16) + (w / 64) , y + cam.Y + (w / 64), w / 32, w / 32);
					itemsCount.draw(b, "" + pl.Inventory.get((pl.hotbar[i])).count, i * (w / 16) + x + cam.X, y + cam.Y + (w / 16));
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
