package com.kgc.sauw.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.kgc.sauw.UI.Elements.Button;
import com.kgc.sauw.UI.Elements.Image;
import com.kgc.sauw.UI.Elements.Slot;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.graphic.Graphic;
import com.kgc.sauw.utils.Camera2D;

import static com.kgc.sauw.graphic.Graphic.BATCH;
import static com.kgc.sauw.graphic.Graphic.TEXTURES;
import static com.kgc.sauw.environment.Environment.ITEMS;
import static com.kgc.sauw.map.World.MAPS;

public class Interface {
    public static class InterfaceSizes {
        public static int STANDART = 0;
        public static int FULL = 1;
    }

    private String ID;

    private int WIDTH = Gdx.graphics.getWidth();
    private int HEIGHT = Gdx.graphics.getHeight();
    public boolean isOpen = false;
    public boolean isBlockInterface;
    private int w, SCREEN_HEIGHT;
    public BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));
    public float width, heigth, x, y;
    public Button exitButton;

    public ArrayList<Button> buttons = new ArrayList<Button>();
    public ArrayList<Image> images = new ArrayList<Image>();
    public ArrayList<Slot> slots = new ArrayList<Slot>();

    private int currX, currY, currZ;
    public InterfaceEvents IE = null;
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

        w = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        text.setColor(64f / 255, 137f / 255, 154f / 255, 1);

        if (size == InterfaceSizes.FULL) {
            width = w;
            heigth = SCREEN_HEIGHT;
            actionBar = Graphic.TEXTURES.generateTexture(16, 1, true);
        } else if (size == InterfaceSizes.STANDART) {
            width = w / 16 * (SCREEN_HEIGHT / (w / 16.0f) - 2);
            heigth = w / 16 * (SCREEN_HEIGHT / (w / 16.0f) - 2);
        }
        x = (w - width) / 2;
        y = (SCREEN_HEIGHT - heigth) / 2;
        this.size = size;
        exitButton = new Button(ID + "_CLOSE_BUTTON", (int) (x + width - w / 16), (int) (y + heigth - w / 16 + w / 64), w / 32, w / 32, Graphic.TEXTURES.closeButton, Graphic.TEXTURES.closeButton);
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
        float xxx = WIDTH / 16;
        float yyy = WIDTH / 32;
        float www = WIDTH / 16 * 8 - WIDTH / 32;
        float hhh = HEIGHT - WIDTH / 16 * 2;
        backgroundInv0 = Graphic.TEXTURES.generateTexture(7.5f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
        backgroundInv2 = Graphic.TEXTURES.generateTexture(6f, (HEIGHT - WIDTH / 16 * 2) / (WIDTH / 16), false);
        previousTabInv = new Button(ID + "_PREVIOUS_INVENTORY_TAB_BUTTON", (int) (xxx + WIDTH / 32), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, Graphic.TEXTURES.button_left_0, Graphic.TEXTURES.button_left_1);
        nextTabInv = new Button(ID + "_NEXT_INVENTORY_TAB_BUTTON", (int) (xxx + www - WIDTH / 32 - hhh / 7), (int) (yyy + hhh - hhh / 7 - WIDTH / 64), (int) hhh / 7, (int) hhh / 7, Graphic.TEXTURES.button_right_0, Graphic.TEXTURES.button_right_1);
        buttons.add(previousTabInv);
        buttons.add(nextTabInv);
        int dist = nextTabInv.X - (previousTabInv.X + previousTabInv.width);
        backgroundInv1 = Graphic.TEXTURES.generateTexture(dist / (WIDTH / 16), (hhh / 7) / (WIDTH / 16), true);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "InventorySlot_" + num;
                Slot s = new Slot(id, (int) (xxx + w / 32 + hhh / 7 * x + w / 64), (int) (yyy + w / 32 + hhh / 7 * (4 - y)), (int) (hhh / 7), (int) (hhh / 7), Graphic.TEXTURES.selected_slot);
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
        if (IE != null) {
            IE.onOpen();
        }
        onOpen();
    }

    public void close() {
        isOpen = false;
        if (IE != null)
            IE.onClose();
        onClose();
    }

    public void swap(Slot a, Slot a1, Player pl) {
        if (a1.SF == null || a1.SF.isValid(a.id, a.count, a.data, a.ID)) {
            int temp = a.id;
            int temp1 = a.count;
            int temp2 = a.data;
            //if (!a.isInventorySlot) {
            if (isBlockInterface) {
                MAPS.map0[currY][currX][currZ].getContainer(a.ID).setItem(a1.id, a1.count, a1.data);
            } else {
                a.id = a1.id;
                a.count = a1.count;
                a.data = a1.data;
            }
			/*} else {
			 pl.Inventory[a.invSlot].setItem(a1.id, a1.count, a1.data);
			 }*/
            //if (!a1.isInventorySlot) {
            if (MAPS != null) {
                MAPS.map0[currY][currX][currZ].getContainer(a1.ID).setItem(temp, temp1, temp2);
            } else {
                a1.id = temp;
                a1.count = temp1;
                a1.data = temp2;
            }
			/*} else {
			 pl.Inventory[a1.invSlot].setItem(temp, temp1, temp2);
			 }*/
        }
    }

    public void sendToSlot(Slot slot1, Slot slot2, Player pl, Camera2D cam) {
        if (slot2.SF == null || slot2.SF.isValid(slot1.id, slot1.count, slot1.data, slot1.ID)) {
            swap(slot1, slot2, pl);
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

    public Slot getTouchedSlot() {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).isTouched()) {
                return slots.get(i);
            }
        }
        return null;
    }

    public void update(Player pl, Camera2D cam) {
        if (isOpen) {
            for (Slot slot : slots) {
                slot.id = 0;
                slot.count = 0;
                slot.data = 0;
            }
            if (inventory) {
                for (int j = 0; j < pl.Inventory.size(); j++) {
                    if (j >= currentTabInv * 30 && j < currentTabInv * 30 + 30) {
                        Slot slot = getSlot("InventorySlot_" + (j - currentTabInv * 30));
                        slot.id = pl.Inventory.get(j).id;
                        slot.count = pl.Inventory.get(j).count;
                        slot.data = pl.Inventory.get(j).data;
                    }
                }
                if (nextTabInv.wasClicked) {
                    if (pl.Inventory.size() > (currentTabInv + 1) * 30) {
                        currentTabInv++;
                    }
                }
                if (previousTabInv.wasClicked) {
                    if (currentTabInv - 1 >= 0) {
                        currentTabInv--;
                    }
                }
            }
            if (MAPS != null) {
                for (int i = 0; i < MAPS.map0[currY][currX][currZ].containers.size(); i++) {
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).id = MAPS.map0[currY][currX][currZ].containers.get(i).getId();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).count = MAPS.map0[currY][currX][currZ].containers.get(i).getCount();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).data = MAPS.map0[currY][currX][currZ].containers.get(i).getData();
                }
            }
            for (Slot slot : slots) {
                slot.update(slots, this, pl, cam);
            }
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).update(cam);
            }
            if (IE != null) {
                IE.tick();
            }
            tick();
            exitButton.update(cam);
        }
    }

    public void render(Player pl, Camera2D cam) {
        if (isOpen) {
            if (size == InterfaceSizes.FULL) {
                BATCH.draw(TEXTURES.standartBackground_full, x + cam.X, y + cam.Y, width, heigth);
            } else if (size == InterfaceSizes.STANDART) {
                BATCH.draw(TEXTURES.standartBackground, x + cam.X, y + cam.Y, width, heigth);
            }
            if (size == InterfaceSizes.FULL) {
                BATCH.draw(actionBar, cam.X, cam.Y + HEIGHT - WIDTH / 16, WIDTH, WIDTH / 16);
            }
            text.drawMultiLine(BATCH, headerText, x + cam.X, (y + heigth + cam.Y) - (w / 16 - text.getCapHeight()) / 2, width, BitmapFont.HAlignment.CENTER);
            if (IE != null) {
                IE.renderBefore();
            }
            preRender();
            if (inventory) {
                BATCH.draw(backgroundInv2, WIDTH / 16 * 9, WIDTH / 32, WIDTH / 16 * 6, HEIGHT - WIDTH / 16 * 2);
                BATCH.draw(backgroundInv0, WIDTH / 16, WIDTH / 32, WIDTH / 16 * 8 - WIDTH / 32, HEIGHT - WIDTH / 16 * 2);
                BATCH.draw(backgroundInv1, previousTabInv.X + previousTabInv.width, previousTabInv.Y, nextTabInv.X - (previousTabInv.X + previousTabInv.width), previousTabInv.height);
            }
            exitButton.render(BATCH, cam);

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).render(BATCH, cam);
            }
            for (int i = 0; i < images.size(); i++) {
                images.get(i).render(BATCH, cam);
            }
            for (Slot slot : slots) {
                slot.render(BATCH, cam);
            }
            for (int i = 0; i < slots.size(); i++) {
                slots.get(i).itemRender(BATCH, ITEMS);
            }
            if (IE != null) {
                IE.render();
            }
            render();
        }
    }

    public void setInterfaceEvents(InterfaceEvents IE) {
        this.IE = IE;
        this.IE.Interface = this;
        this.IE.initialize();
    }

    public void tick() {
    }

    public void onOpen() {
    }

    public void onClose() {
    }

    public void preRender() {
    }

    public void render() {
    }
}
