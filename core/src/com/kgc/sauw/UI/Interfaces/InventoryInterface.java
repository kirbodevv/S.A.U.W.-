package com.kgc.sauw.UI.Interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kgc.sauw.UI.Elements.Image;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.UI.Elements.Text;
import com.kgc.sauw.UI.Interface;

import java.text.DecimalFormat;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.SCREEN_WIDTH;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;

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

    Image playerImg;
    Text playerWeight;

    public InventoryInterface() {
        super("INVENTORY_INTERFACE");
        createFromXml(Gdx.files.internal("xml/InventoryInterface.xml").readString());

        playerWeight = (Text) getElement("INVENTORY_INTERFACE_playerWeight");
        playerImg = (Image) getElement("INVENTORY_INTERFACE_playerImg");

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

        /*Layout PlayerInfoLayout = new Layout(Layout.Orientation.VERTICAL);
        Layout hotbarLayout = new Layout(Layout.Orientation.HORIZONTAL);

        PlayerInfoLayout.setGravity(Layout.Gravity.TOP);
        PlayerInfoLayout.setSize(Size.WRAP_CONTENT, Size.WRAP_CONTENT);
        PlayerInfoLayout.setTranslationY(-BLOCK_SIZE);
        PlayerInfoLayout.setMarginBottom(BLOCK_SIZE / 8f);

        hotbarLayout.setGravity(Gravity.LEFT);
        hotbarLayout.setSize(Size.WRAP_CONTENT, Size.WRAP_CONTENT);

        playerImg = new Image(0, 0, plW, plH);
        playerImg.setMarginBottom(BLOCK_SIZE / 4f);

        playerWeight = new Text();
        playerWeight.setSize(BLOCK_SIZE * 4f, BLOCK_SIZE / 2f);

        PlayerInfoLayout.addElements(playerImg, playerWeight);

        background0 = Textures.generateTexture(6f, 0.25f, true);
        background1 = Textures.generateTexture(1f, 1f, true);*/

        for (int i = 0; i < 8; i++) {
            final int ii = i;
            final Slot s = (Slot) getElement("INVENTORY_INTERFACE_hotbarslot_" + i);
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
        }
        updateElementsList();
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
        playerImg.setImg(currentFrame);
        playerWeight.setText(DF.format(PLAYER.weight) + " | " + DF.format(PLAYER.maxWeight) + " Kg");
    }

    @Override
    public void postRender() {
        /*text.drawMultiLine(BATCH, DF.format(PLAYER.weight) + " | " + DF.format(PLAYER.maxWeight) + " Kg", SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 64, SCREEN_HEIGHT - BLOCK_SIZE / 2f * 9 - BLOCK_SIZE / 4f, SCREEN_WIDTH / 16 * 3, BitmapFont.HAlignment.LEFT);
        BATCH.draw(currentFrame, SCREEN_WIDTH / 16 * 12 - plW / 2f, SCREEN_HEIGHT - BLOCK_SIZE * 5 + BLOCK_SIZE / 4f, plW, plH);
        BATCH.draw(background0, SCREEN_WIDTH / 16 * 9, SCREEN_HEIGHT - BLOCK_SIZE * 6 - BLOCK_SIZE / 2f, SCREEN_WIDTH / 16 * 6, SCREEN_WIDTH / 64);
        if (currentItemInv != -1 && currentItemInv < PLAYER.Inventory.size()) {
            BATCH.draw(background1, SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 128, SCREEN_HEIGHT - BLOCK_SIZE * 8f, BLOCK_SIZE * 1.25f, BLOCK_SIZE * 1.25f);
            BATCH.draw(ITEMS.getTextureById(PLAYER.Inventory.get(currentItemInv).id), SCREEN_WIDTH / 16 * 9 + SCREEN_WIDTH / 128, SCREEN_HEIGHT - BLOCK_SIZE * 8f, BLOCK_SIZE * 1.25f, BLOCK_SIZE * 1.25f);
            text.drawMultiLine(BATCH, ITEMS.getItemById(PLAYER.Inventory.get(currentItemInv).id).getItemConfiguration().weight + " Kg", BLOCK_SIZE * 10.25f + SCREEN_WIDTH / 64, SCREEN_HEIGHT - BLOCK_SIZE * 6.75f, SCREEN_WIDTH / 16 * 4, BitmapFont.HAlignment.LEFT);
            text.drawMultiLine(BATCH, ITEMS.getNameById(PLAYER.Inventory.get(currentItemInv).id), BLOCK_SIZE * 10.25f + SCREEN_WIDTH / 64, SCREEN_HEIGHT - BLOCK_SIZE * 7.25f, SCREEN_WIDTH / 16 * 4, BitmapFont.HAlignment.LEFT);
        }*/
    }
}
