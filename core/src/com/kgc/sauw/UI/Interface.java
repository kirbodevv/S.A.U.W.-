package com.kgc.sauw.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.Image;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.graphic.Graphic;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.resource.Textures;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Interface {
    public static class InterfaceSizes {
        public static int STANDART = 0;
        public static int FULL = 1;
    }

    private String ID;

    public boolean isOpen = false;
    public boolean isBlockInterface;
    public BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));
    public float width, heigth, x, y;
    public Button exitButton;

    public ArrayList<Button> buttons = new ArrayList<Button>();
    public ArrayList<Image> images = new ArrayList<Image>();
    public ArrayList<Slot> slots = new ArrayList<Slot>();

    private int currX, currY, currZ;
    private String headerText = "";
    private int size = 0;

    private Texture actionBar;

    private boolean inventory = false;
    private Texture backgroundInv0;
    private Texture backgroundInv1;
    private Texture backgroundInv2;
    public Button previousTabInv;
    public Button nextTabInv;
    public int currentItemInv = -1;
    public int currentTabInv = 0;

    public Interface(int size, String ID) {
        this.ID = ID;

        text.setColor(64f / 255, 137f / 255, 154f / 255, 1);

        if (size == InterfaceSizes.FULL) {
            width = Graphic.SCREEN_WIDTH;
            heigth = Graphic.SCREEN_HEIGHT;
            actionBar = TEXTURES.generateTexture(16, 1, true);
        } else if (size == InterfaceSizes.STANDART) {
            width = Graphic.SCREEN_WIDTH / 16 * (Graphic.SCREEN_HEIGHT / (Graphic.SCREEN_WIDTH / 16.0f) - 2);
            heigth = Graphic.SCREEN_WIDTH / 16 * (Graphic.SCREEN_HEIGHT / (Graphic.SCREEN_WIDTH / 16.0f) - 2);
        }
        x = (Graphic.SCREEN_WIDTH - width) / 2;
        y = (Graphic.SCREEN_HEIGHT - heigth) / 2;
        this.size = size;
        exitButton = new Button(ID + "_CLOSE_BUTTON", (int) (x + width - Graphic.SCREEN_WIDTH / 16), (int) (y + heigth - Graphic.SCREEN_WIDTH / 16 + Graphic.SCREEN_WIDTH / 64), (int) Graphic.SCREEN_WIDTH / 32, (int) Graphic.SCREEN_WIDTH / 32, TEXTURES.closeButton, TEXTURES.closeButton);
        exitButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });
    }

    public Interface setHeaderText(String text) {
        this.headerText = text;
        return this;
    }

    public Interface isBlockInterface(boolean b) {
        isBlockInterface = b;
        return this;
    }

    public Interface createInventory() {
        inventory = true;
        float xxx = SCREEN_WIDTH / 16;
        float www = SCREEN_WIDTH / 16 * 8 - SCREEN_WIDTH / 32;
        float hhh = (BLOCK_SIZE * 7.5f - BLOCK_SIZE * 2) / 6;
        backgroundInv0 = Textures.generateTexture(8f, (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2) / (SCREEN_WIDTH / 16), false);
        backgroundInv2 = Textures.generateTexture(6f, (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2) / (SCREEN_WIDTH / 16), false);
        previousTabInv = new Button(ID + "_PREVIOUS_INVENTORY_TAB_BUTTON", (int) (xxx + SCREEN_WIDTH / 32), (int) SCREEN_HEIGHT - BLOCK_SIZE * 3, (int) hhh, (int) hhh, TEXTURES.button_left_0, TEXTURES.button_left_1);
        nextTabInv = new Button(ID + "_NEXT_INVENTORY_TAB_BUTTON", (int) (xxx + www - SCREEN_WIDTH / 32 - hhh), previousTabInv.Y, (int) hhh, (int) hhh, TEXTURES.button_right_0, TEXTURES.button_right_1);
        buttons.add(previousTabInv);
        buttons.add(nextTabInv);
        int dist = nextTabInv.X - (previousTabInv.X + previousTabInv.width);
        backgroundInv1 = Textures.generateTexture(dist / BLOCK_SIZE, hhh / BLOCK_SIZE, true);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "InventorySlot_" + num;
                Slot s = new Slot(id, (int) (hhh * x + BLOCK_SIZE + BLOCK_SIZE), (int) (hhh * (4 - y) + SCREEN_HEIGHT - BLOCK_SIZE / 2 * 7 - (hhh * 5)), (int) (hhh), (int) (hhh));
                s.isInventorySlot = true;
                s.setSF(new Slot.SlotFunctions() {

                    @Override
                    public boolean isValid(int id, int count, int data, String FromSlotWithId) {
                        return true;
                    }

                    @Override
                    public void onClick() {
                        currentItemInv = currentTabInv * 30 + num;
                    }
                });
                slots.add(s);

            }
        }
        return this;
    }

    public void open(int x, int y, int z) {
        isOpen = true;
        currentItemInv = -1;
        currX = x;
        currY = y;
        currZ = z;
    }

    public void open() {
        isOpen = true;
        currentItemInv = -1;
        onOpen();
    }

    public void close() {
        isOpen = false;
        onClose();
    }

    public void swap(Slot a, Slot a1) {
        if (a1.SF == null || a1.SF.isValid(a.id, a.count, a.data, a.ID)) {
            int temp = a.id;
            int temp1 = a.count;
            int temp2 = a.data;
            if (a.isInventorySlot) {
                if (!a1.isInventorySlot && a1.id == 0) {
                    MAPS.map0[currY][currX][currZ].getContainer(a1.ID).setItem(a.id, a.count, a.data);
                    PLAYER.Inventory.remove(PLAYER.Inventory.get(a.inventorySlot));

                }
            } else {
                if (a1.isInventorySlot) {
                    PLAYER.addItem(a.id, a.count, a.data);
                    MAPS.map0[currY][currX][currZ].getContainer(a.ID).setItem(0, 0, 0);
                } else {
                    MAPS.map0[currY][currX][currZ].getContainer(a.ID).setItem(a1.id, a1.count, a1.data);
                    MAPS.map0[currY][currX][currZ].getContainer(a1.ID).setItem(temp, temp1, temp2);
                }
            }
        }
    }

    public void sendToSlot(Slot slot1, Slot slot2) {
        if (slot2.SF == null || slot2.SF.isValid(slot1.id, slot1.count, slot1.data, slot1.ID)) {
            swap(slot1, slot2);
        }
    }

    public Slot getSlot(String ID) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).ID.equals(ID)) {
                return slots.get(i);
            }
        }
        return null;
    }

    public Button getButton(String ID) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).ID.equals(ID)) {
                return buttons.get(i);
            }
        }
        return null;
    }

    public void update(boolean isGameInterface) {
        if (isOpen) {
            for (Slot slot : slots) {
                slot.id = 0;
                slot.count = 0;
                slot.data = 0;
            }
            if (inventory) {
                for (int j = 0; j < PLAYER.Inventory.size(); j++) {
                    if (j >= currentTabInv * 30 && j < currentTabInv * 30 + 30) {
                        Slot slot = getSlot("InventorySlot_" + (j - currentTabInv * 30));
                        slot.inventorySlot = j;
                        slot.id = PLAYER.Inventory.get(j).id;
                        slot.count = PLAYER.Inventory.get(j).count;
                        slot.data = PLAYER.Inventory.get(j).data;
                    }
                }
                if (nextTabInv.wasClicked) {
                    if (PLAYER.Inventory.size() > (currentTabInv + 1) * 30) {
                        currentTabInv++;
                    }
                }
                if (previousTabInv.wasClicked) {
                    if (currentTabInv - 1 >= 0) {
                        currentTabInv--;
                    }
                }
            }
            if (isGameInterface) {
                for (int i = 0; i < MAPS.map0[currY][currX][currZ].containers.size(); i++) {
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).id = MAPS.map0[currY][currX][currZ].containers.get(i).getId();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).count = MAPS.map0[currY][currX][currZ].containers.get(i).getCount();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).data = MAPS.map0[currY][currX][currZ].containers.get(i).getData();
                }
            }
            for (Slot slot : slots) {
                slot.update(slots, this, PLAYER, INTERFACE_CAMERA);
            }
            for (Button button : buttons) {
                button.update(INTERFACE_CAMERA);
            }
            tick();
            if (isBlockInterface) tick(MAPS.map0[currY][currX][currZ]);
            exitButton.update(INTERFACE_CAMERA);
        }
    }

    public void render() {
        if (isOpen) {
            if (size == InterfaceSizes.FULL) {
                BATCH.draw(TEXTURES.standartBackground_full, x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y, width, heigth);
            } else if (size == InterfaceSizes.STANDART) {
                BATCH.draw(TEXTURES.standartBackground, x + INTERFACE_CAMERA.X, y + INTERFACE_CAMERA.Y, width, heigth);
            }
            if (size == InterfaceSizes.FULL) {
                BATCH.draw(actionBar, INTERFACE_CAMERA.X, INTERFACE_CAMERA.Y + SCREEN_HEIGHT - SCREEN_WIDTH / 16, SCREEN_WIDTH, SCREEN_WIDTH / 16);
            }
            text.drawMultiLine(BATCH, headerText, x + INTERFACE_CAMERA.X, (y + heigth + INTERFACE_CAMERA.Y) - (SCREEN_WIDTH / 16 - text.getCapHeight()) / 2, width, BitmapFont.HAlignment.CENTER);
            preRender();
            if (inventory) {
                BATCH.draw(backgroundInv2, SCREEN_WIDTH / 16 * 9, SCREEN_WIDTH / 32, SCREEN_WIDTH / 16 * 6, SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2);
                BATCH.draw(backgroundInv0, SCREEN_WIDTH / 16, SCREEN_WIDTH / 32, SCREEN_WIDTH / 16 * 8 - SCREEN_WIDTH / 32, SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2);
                BATCH.draw(backgroundInv1, previousTabInv.X + previousTabInv.width, previousTabInv.Y, nextTabInv.X - (previousTabInv.X + previousTabInv.width), previousTabInv.height);
                text.drawMultiLine(BATCH, LANGUAGES.getString("backpack"), previousTabInv.X + previousTabInv.width, previousTabInv.Y + previousTabInv.height - ((previousTabInv.height - text.getCapHeight()) / 2), nextTabInv.X - (previousTabInv.X + previousTabInv.width), BitmapFont.HAlignment.CENTER);
            }
            exitButton.render(BATCH, INTERFACE_CAMERA);

            for (Button button : buttons) {
                button.render(BATCH, INTERFACE_CAMERA);
            }
            for (Image image : images) {
                image.render(BATCH, INTERFACE_CAMERA);
            }
            for (Slot slot : slots) {
                slot.render(BATCH, INTERFACE_CAMERA);
            }
            for (Slot slot : slots) {
                slot.itemRender();
            }
            postRender();
        }
    }

    public void tick() {
    }

    public void tick(Tile tile) {
    }

    public void onOpen() {
    }

    public void onClose() {
    }

    public void preRender() {
    }

    public void postRender() {
    }
}