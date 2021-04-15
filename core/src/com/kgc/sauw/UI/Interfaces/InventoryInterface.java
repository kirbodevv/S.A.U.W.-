package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.resource.Textures;

import java.text.DecimalFormat;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.utils.Languages.LANGUAGES;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.graphic.Graphic.BATCH;

public class InventoryInterface extends Interface {
    int plW = (int) (SCREEN_WIDTH / 24 * 4 * 10 / 26);
    int plH = (int) (SCREEN_WIDTH / 24 * 4);
    Texture background0;
    Texture background1;
    Slot[] hotbarslots = new Slot[8];
    DecimalFormat DF = new DecimalFormat("#.##");
    private TextureRegion[] playerAnimFrames;
    private TextureRegion currentFrame;
    private float stateTime = 0.0f;
    private float timer = 0.0f;

    Animation playerAnim;
    Animation tiredPlayerAnim;


    public InventoryInterface() {
        super(InterfaceSizes.FULL, "INVENTORY_INTERFACE");
        setHeaderText(LANGUAGES.getString("inventory")).isBlockInterface(false).createInventory();

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

        background0 = Textures.generateTexture(6f, 0.25f, true);
        background1 = Textures.generateTexture(2f, 2f, true);
        int slotW = (int) SCREEN_WIDTH / 32 * 11 / 8;
        for (int i = 0; i < 8; i++) {
            final int ii = i;
            final Slot s = new Slot("hotbarslot_" + i, (int) (SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 64 + slotW * i), (int) (SCREEN_WIDTH / 32 * 7 - SCREEN_WIDTH / 64), slotW, slotW, TEXTURES.selected_slot);
            s.setSF(new Slot.SlotFunctions() {
                @Override
                public void onClick() {
                    PLAYER.hotbar[ii] = -1;
                }

                @Override
                public boolean isValid(int id, int count, int data, String FromSlotWithId) {
                    if (FromSlotWithId.contains("InventorySlot_")) {
                        for (int i = 0; i < PLAYER.hotbar.length; i++) {
                            if (PLAYER.hotbar[i] == currentTabInv * 30 + Integer.parseInt(FromSlotWithId.substring(14))) {
                                PLAYER.hotbar[i] = -1;
                            }
                        }
                        PLAYER.hotbar[ii] = currentTabInv * 30 + Integer.parseInt(FromSlotWithId.substring(14));
                    }

                    return false;
                }
            });
            hotbarslots[i] = s;
            slots.add(s);
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < hotbarslots.length; i++) {
            if (PLAYER.hotbar[i] != -1) {
                hotbarslots[i].id = PLAYER.getItemFromHotbar(i).id;
                hotbarslots[i].count = PLAYER.Inventory.get(PLAYER.hotbar[i]).count;
            }
        }
        timer += Gdx.graphics.getRawDeltaTime();
        if (timer >= 6) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (PLAYER.weight < PLAYER.maxWeight) {
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
    public void render() {
        text.drawMultiLine(BATCH, DF.format(PLAYER.weight) + " | " + DF.format(PLAYER.maxWeight) + "Kg", SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 64, SCREEN_WIDTH / 32 * 9, SCREEN_WIDTH / 16 * 3, BitmapFont.HAlignment.LEFT);
        text.drawMultiLine(BATCH, LANGUAGES.getString("backpack"), previousTabInv.X + previousTabInv.width, previousTabInv.Y + previousTabInv.height - ((previousTabInv.height - text.getCapHeight()) / 2), nextTabInv.X - (previousTabInv.X + previousTabInv.width), BitmapFont.HAlignment.CENTER);
        BATCH.draw(currentFrame, SCREEN_WIDTH / 16 * 12 - plW / 2, SCREEN_WIDTH / 16 * 4, plW, plH);
        BATCH.draw(background0, SCREEN_WIDTH / 16 * 9, SCREEN_WIDTH / 16 * 3 - SCREEN_WIDTH / 64, SCREEN_WIDTH / 16 * 6, SCREEN_WIDTH / 64);
        if (currentItemInv != -1 && currentItemInv < PLAYER.Inventory.size()) {
            BATCH.draw(background1, SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 128, SCREEN_WIDTH / 32 + SCREEN_WIDTH / 128, SCREEN_WIDTH / 16 * 2, SCREEN_WIDTH / 16 * 2);
            BATCH.draw(ITEMS.getTextureById(PLAYER.Inventory.get(currentItemInv).id), SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 128, SCREEN_WIDTH / 32 + SCREEN_WIDTH / 128, SCREEN_WIDTH / 16 * 2, SCREEN_WIDTH / 16 * 2);
            text.drawMultiLine(BATCH, ITEMS.getItemById(PLAYER.Inventory.get(currentItemInv).id).weight + " Kg", SCREEN_WIDTH / 16 * 11 + SCREEN_WIDTH / 64, SCREEN_WIDTH / 32 * 4, SCREEN_WIDTH / 16 * 4, BitmapFont.HAlignment.LEFT);
            text.drawMultiLine(BATCH, ITEMS.getNameById(PLAYER.Inventory.get(currentItemInv).id), SCREEN_WIDTH / 16 * 11 + SCREEN_WIDTH / 64, SCREEN_WIDTH / 32 * 5, SCREEN_WIDTH / 16 * 4, BitmapFont.HAlignment.LEFT);
        }
    }
}
