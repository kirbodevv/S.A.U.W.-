package com.kgc.sauw.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kgc.sauw.UI.Elements.*;
import com.kgc.sauw.graphic.Graphic;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.resource.Textures;

import java.util.ArrayList;

import static com.kgc.sauw.entity.Entities.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Interface {
    private String ID;

    public boolean isOpen = false;
    public boolean isBlockInterface;
    public BitmapFont text = new BitmapFont(Gdx.files.internal("ttf.fnt"));
    public float width, heigth, x, y;
    public Button exitButton;

    public ArrayList<InterfaceElement> Elements = new ArrayList<InterfaceElement>();
    public ArrayList<Slot> slots = new ArrayList<>();
    private int currX, currY, currZ;
    private String headerText = "";

    private Image actionBar;

    private boolean inventory = false;
    public Button previousTabInv;
    public Button nextTabInv;
    public int currentItemInv = -1;
    public int currentTabInv = 0;

    public Layout MainLayout;

    protected Layout inventoryLayout;
    protected Layout switchTabLayout;
    protected Layout slotsLayout;
    protected Layout optionalLayout;

    public Interface(String ID) {
        this.ID = ID;

        text.setColor(64f / 255, 137f / 255, 154f / 255, 1);
        MainLayout = new Layout(Layout.Orientation.VERTICAL);

        width = Graphic.SCREEN_WIDTH;
        heigth = Graphic.SCREEN_HEIGHT;

        actionBar = new Image(0, (int) (SCREEN_HEIGHT - BLOCK_SIZE), (int) SCREEN_WIDTH, BLOCK_SIZE);
        actionBar.setImg(Textures.generateTexture(16, 1, true));

        MainLayout.setBackground(TEXTURES.standartBackground_full);
        MainLayout.setSize(Layout.Size.MATCH_PARENT, Layout.Size.FIXED_SIZE);
        MainLayout.setSize(0, SCREEN_HEIGHT - BLOCK_SIZE);
        MainLayout.setGravity(Layout.Gravity.TOP);
        MainLayout.setID("MainLayout");

        x = (Graphic.SCREEN_WIDTH - width) / 2;
        y = (Graphic.SCREEN_HEIGHT - heigth) / 2;

        exitButton = new Button("CLOSE_BUTTON", (int) (x + width - Graphic.SCREEN_WIDTH / 16), (int) (y + heigth - Graphic.SCREEN_WIDTH / 16 + Graphic.SCREEN_WIDTH / 64), (int) Graphic.SCREEN_WIDTH / 32, (int) Graphic.SCREEN_WIDTH / 32, TEXTURES.closeButton, TEXTURES.closeButton);
        exitButton.setEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });
    }

    public void createFromXml(String XMLString) {
        try {
            XmlInterfaceLoader.load(this, XMLString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementInInterface(InterfaceElement e) {
        for (InterfaceElement e1 : Elements) {
            if (e1.equals(e)) return true;
        }
        return false;
    }

    private boolean isElementInSlotsArray(Slot slot) {
        for (Slot slot1 : slots) {
            if (slot1.equals(slot)) return true;
        }
        return false;
    }

    public void updateElementsList() {
        for (InterfaceElement e : MainLayout.getAllElements()) {
            if (!isElementInInterface(e)) Elements.add(e);
        }
        for (InterfaceElement e : Elements) {
            if (e instanceof Slot) {
                if (!isElementInSlotsArray((Slot) e)) slots.add((Slot) e);
            }
        }
    }

    public Interface setHeaderText(String text) {
        this.headerText = text;
        return this;
    }

    public Interface isBlockInterface(boolean b) {
        isBlockInterface = b;
        return this;
    }

    public void createInventory() {
        inventory = true;

        inventoryLayout = new Layout(Layout.Orientation.VERTICAL);
        switchTabLayout = new Layout(Layout.Orientation.HORIZONTAL);
        slotsLayout = new Layout(Layout.Orientation.VERTICAL);
        optionalLayout = new Layout(Layout.Orientation.VERTICAL);


        MainLayout.setOrientation(Layout.Orientation.HORIZONTAL);
        MainLayout.setGravity(Layout.Gravity.LEFT);

        inventoryLayout.setGravity(Layout.Gravity.TOP);
        inventoryLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        inventoryLayout.setSize(BLOCK_SIZE * 7.5f, SCREEN_HEIGHT - BLOCK_SIZE * 2f);
        inventoryLayout.setBackground(Textures.generateTexture(7.5f, SCREEN_HEIGHT / BLOCK_SIZE - 2f, false));
        inventoryLayout.setTranslationX(BLOCK_SIZE);
        inventoryLayout.setID("inventoryLayout");

        switchTabLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        switchTabLayout.setGravity(Layout.Gravity.LEFT);
        switchTabLayout.setTranslationY(-(BLOCK_SIZE * 0.25f));
        switchTabLayout.setID("switchTabLayout");

        slotsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        slotsLayout.setGravity(Layout.Gravity.TOP);
        slotsLayout.setTranslationY(-BLOCK_SIZE / 4f);
        slotsLayout.setID("slotsLayout");

        optionalLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        optionalLayout.setSize(BLOCK_SIZE * 6f, SCREEN_HEIGHT - BLOCK_SIZE * 2);
        optionalLayout.setGravity(Layout.Gravity.TOP);
        optionalLayout.setBackground(Textures.generateTexture(6f, (SCREEN_HEIGHT - SCREEN_WIDTH / 16 * 2) / (SCREEN_WIDTH / 16), false));
        optionalLayout.setTranslationX(BLOCK_SIZE / 2f);
        optionalLayout.setID("optionalLayout");

        previousTabInv = new Button("PREVIOUS_INVENTORY_TAB_BUTTON", 0, 0, BLOCK_SIZE, BLOCK_SIZE, TEXTURES.button_left_0, TEXTURES.button_left_1);
        nextTabInv = new Button("NEXT_INVENTORY_TAB_BUTTON", 0, 0, BLOCK_SIZE, BLOCK_SIZE, TEXTURES.button_right_0, TEXTURES.button_right_1);

        Text backpackText = new Text();
        backpackText.setSize(BLOCK_SIZE * 5, BLOCK_SIZE);
        backpackText.setText(LANGUAGES.getString("backpack"));
        backpackText.setID("BackpackText");

        switchTabLayout.addElements(previousTabInv, backpackText, nextTabInv);
        inventoryLayout.addElements(switchTabLayout, slotsLayout);

        for (int y = 0; y < 5; y++) {
            Layout l = new Layout(Layout.Orientation.HORIZONTAL);
            l.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
            l.setGravity(Layout.Gravity.LEFT);
            l.setID("InventoryLayout_" + y);
            for (int x = 0; x < 6; x++) {
                final int num = y * 6 + x;
                String id = "InventorySlot_" + num;
                Slot s = new Slot(id, this, 0, 0, BLOCK_SIZE, BLOCK_SIZE);
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
                l.addElements(s);
            }
            slotsLayout.addElements(l);
        }
        MainLayout.addElements(inventoryLayout, optionalLayout);
        updateElementsList();
    }

    public void open(int x, int y, int z) {
        isOpen = true;
        currentItemInv = -1;
        currX = x;
        currY = y;
        currZ = z;
        onOpen();
        onOpen(MAPS.map0[y][x][z]);
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
        int temp = a.id;
        int temp1 = a.count;
        int temp2 = a.data;
        if (a.isInventorySlot) {
            if (!a1.isInventorySlot && a1.id == 0) {
                MAPS.map0[currY][currX][currZ].getContainer(a1.ID).setItem(a.id, a.count, a.data);
                System.out.println(PLAYER.Inventory.size());
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

    public void sendToSlot(Slot slot1, Slot slot2) {
        if (slot2.SF == null || slot2.SF.isValid(slot1.id, slot1.count, slot1.data, slot1.ID)) {
            swap(slot1, slot2);
        }
    }

    public Slot getSlot(String ID) {
        for (Slot slot : slots) {
            if (slot.ID.equals(ID)) {
                return slot;
            }
        }
        return null;
    }

    public InterfaceElement getElement(String ID) {
        for (InterfaceElement element : Elements) {
            if (element.ID.equals(ID)) {
                return element;
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
            MainLayout.update(INTERFACE_CAMERA);
            if (isGameInterface) {
                for (int i = 0; i < MAPS.map0[currY][currX][currZ].containers.size(); i++) {
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).id = MAPS.map0[currY][currX][currZ].containers.get(i).getId();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).count = MAPS.map0[currY][currX][currZ].containers.get(i).getCount();
                    getSlot(MAPS.map0[currY][currX][currZ].containers.get(i).ID).data = MAPS.map0[currY][currX][currZ].containers.get(i).getData();
                }
            }
            tick();
            if (isBlockInterface) tick(MAPS.map0[currY][currX][currZ]);
            exitButton.update(INTERFACE_CAMERA);
        }
    }

    public void render() {
        if (isOpen) {
            MainLayout.render(BATCH, INTERFACE_CAMERA);

            actionBar.render(BATCH, INTERFACE_CAMERA);
            text.drawMultiLine(BATCH, headerText, x + INTERFACE_CAMERA.X, (y + heigth + INTERFACE_CAMERA.Y) - (SCREEN_WIDTH / 16 - text.getCapHeight()) / 2, width, BitmapFont.HAlignment.CENTER);

            preRender();

            exitButton.render(BATCH, INTERFACE_CAMERA);

            for (InterfaceElement e : Elements) {
                if (e instanceof Slot) ((Slot) e).itemRender();
            }
            postRender();
        }
    }

    public void tick() {
    }

    public void tick(Tile tile) {
    }

    public void onOpen() {
        MainLayout.hide(false);
        for (InterfaceElement e : Elements) {
            e.hide(false);
        }
    }

    public void onOpen(Tile tile) {

    }

    public void onClose() {
        MainLayout.hide(true);
        for (InterfaceElement e : Elements) {
            e.hide(true);
        }
    }

    public void preRender() {
    }

    public void postRender() {
    }
}