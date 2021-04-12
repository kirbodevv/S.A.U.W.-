package com.kgc.sauw.UI.Elements;

import com.badlogic.gdx.Application;
import com.kgc.sauw.utils.Camera2D;
import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.utils.Languages;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.UI.InterfaceEvents;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.environment.Items;
import com.kgc.sauw.resource.Textures;

import java.text.DecimalFormat;

import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.environment.Environment.LANGUAGES;
import static com.kgc.sauw.graphic.Graphic.*;

public class Inventory {
    private int w, h;
    private BitmapFont itemsCount = new BitmapFont(Gdx.files.internal("ttf.fnt"));;
    private Texture Texture;
    private Texture Texture2;
    private boolean hided = false;
    private boolean isTouched = false;
    private int x, y;
    private Button openButton;
    public Interface inventoryInterface;
    private TextureRegion[] playerAnimFrames;
    private TextureRegion currentFrame;
    private float stateTime = 0.0f;
    private float timer = 0.0f;

    Animation playerAnim;
    Animation tiredPlayerAnim;

    public Inventory(Texture Texture, Texture Texture2, int x, int y) {
        w = INTERFACE_CAMERA.W;
        h = INTERFACE_CAMERA.H;
        itemsCount.setColor(Color.BLACK);
        itemsCount.scale(w / 2500);
        this.Texture = Texture;
        this.Texture2 = Texture2;
        this.x = x;
        this.y = y;
        openButton = new Button("", x + w / 16 * 8, y, w / 16, w / 16, TEXTURES.extraButton_0, TEXTURES.extraButton_1);
        inventoryInterface = new Interface(Interface.InterfaceSizes.FULL);
        inventoryInterface.setHeaderText(LANGUAGES.getString("inventory")).isBlockInterface(false).createInventory();
        openButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                inventoryInterface.open();
            }
        });
        TextureRegion[][] tmp = TextureRegion.split(TEXTURES.player_inv, TEXTURES.player_inv.getWidth() / 3, TEXTURES.player_inv.getHeight());
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

    public void init(final Player pl, final Textures textures, final Languages languages) {

        inventoryInterface.setInterfaceEvents(new InterfaceEvents() {
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
                    s.setSF(new Slot.SlotFunctions() {
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
            public void onClose() {

            }

            @Override
            public void renderBefore() {

            }

            @Override
            public void render() {
                Interface.text.drawMultiLine(BATCH, DF.format(pl.weight) + " | " + DF.format(pl.maxWeight) + "Kg", w / 16 * 9 + w / 64, w / 32 * 9, w / 16 * 3, BitmapFont.HAlignment.LEFT);
                Interface.text.drawMultiLine(BATCH, languages.getString("backpack"), Interface.previosTabInv.X + Interface.previosTabInv.width, Interface.previosTabInv.Y + Interface.previosTabInv.height - ((Interface.previosTabInv.height - Interface.text.getCapHeight()) / 2), Interface.nextTabInv.X - (Interface.previosTabInv.X + Interface.previosTabInv.width), BitmapFont.HAlignment.CENTER);
                BATCH.draw(currentFrame, w / 16 * 12 - plW / 2, w / 16 * 4, plW, plH);
                BATCH.draw(background0, w / 16 * 9, w / 16 * 3 - w / 64, w / 16 * 6, w / 64);
                if (Interface.currentItemInv != -1 && Interface.currentItemInv < pl.Inventory.size()) {
                    BATCH.draw(background1, w / 16 * 9 + w / 128, w / 32 + w / 128, w / 16 * 2, w / 16 * 2);
                    BATCH.draw(ITEMS.getTextureById(pl.Inventory.get(Interface.currentItemInv).id), w / 16 * 9 + w / 128, w / 32 + w / 128, w / 16 * 2, w / 16 * 2);
                    Interface.text.drawMultiLine(BATCH, ITEMS.getItemById(pl.Inventory.get(Interface.currentItemInv).id).weight + " Kg", w / 16 * 11 + w / 64, w / 32 * 4, w / 16 * 4, BitmapFont.HAlignment.LEFT);
                    Interface.text.drawMultiLine(BATCH, ITEMS.getNameById(pl.Inventory.get(Interface.currentItemInv).id), w / 16 * 11 + w / 64, w / 32 * 5, w / 16 * 4, BitmapFont.HAlignment.LEFT);
                }
            }
        });
    }

    public void update(Player pl) {
        inventoryInterface.update(pl, INTERFACE_CAMERA);
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            openButton.hide(true);
        }
        if (!hided) {
            openButton.update(INTERFACE_CAMERA);
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
            BATCH.setColor(1, 1, 1, 0.8f);
            BATCH.draw(Texture, x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y, w / 16 * 8, w / 16);
            for (int i = 0; i < 8; i++) {
                if (Gdx.input.getX() > (i * (w / 16)) + x && Gdx.input.getX() < (i * (w / 16)) + x + (w / 16) && Gdx.input.getY() < h && Gdx.input.getY() > h - (w / 16)) {
                    pl.carriedSlot = i;
                }
            }
            openButton.render(BATCH, INTERFACE_CAMERA);
            BATCH.setColor(1, 1, 1, 1);
            BATCH.draw(Texture2, x + INTERFACE_CAMERA.X + (pl.carriedSlot * (w / 16)), y + INTERFACE_CAMERA.Y, w / 16, w / 16);
            for (int i = 0; i < 8; i++) {
                if (pl.hotbar[i] != -1) {
                    BATCH.draw(ITEMS.getTextureById(pl.getItemFromHotbar(i).id), x + INTERFACE_CAMERA.X + i * (w / 16) + (w / 64), y + INTERFACE_CAMERA.Y + (w / 64), w / 32, w / 32);
                    itemsCount.draw(BATCH, "" + pl.Inventory.get((pl.hotbar[i])).count, i * (w / 16) + x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y + (w / 16));
                }
            }
        }
        inventoryInterface.render(pl, INTERFACE_CAMERA);
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
