package com.kgc.sauw.gui;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.graphic.Graphic;
import com.kgc.sauw.map.Tile;
import com.kgc.sauw.gui.elements.Button;
import com.kgc.sauw.gui.elements.Layout;
import com.kgc.sauw.gui.elements.Slot;
import com.kgc.sauw.gui.elements.Text;

import java.util.ArrayList;

import static com.kgc.sauw.entity.EntityManager.PLAYER;
import static com.kgc.sauw.graphic.Graphic.*;
import static com.kgc.sauw.map.World.MAPS;
import static com.kgc.sauw.utils.Languages.LANGUAGES;

public class Interface {
    public String ID;

    public boolean isOpen = false;
    public boolean isBlockInterface;
    public float width, height, x, y;
    public Button closeInterfaceButton;

    public ArrayList<InterfaceElement> Elements = new ArrayList<>();
    public ArrayList<Slot> slots = new ArrayList<>();

    public int currX, currY, currZ;

    private final Text actionBar;

    private boolean inventory = false;
    public Button previousTabInv;
    public Button nextTabInv;
    public int currentItemInv = -1;
    public int currentTabInv = 0;

    public Layout mainLayout;

    protected Layout inventoryLayout;
    protected Layout switchTabLayout;
    protected Layout slotsLayout;
    protected Layout optionalLayout;

    public Interface(String ID) {
        this.ID = ID;

        mainLayout = new Layout(Layout.Orientation.VERTICAL);

        width = Graphic.SCREEN_WIDTH;
        height = Graphic.SCREEN_HEIGHT;

        actionBar = new Text();
        actionBar.setSize(SCREEN_WIDTH, BLOCK_SIZE);

        mainLayout.setBackground(TEXTURES.standardBackground);
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 1);
        mainLayout.setGravity(Layout.Gravity.TOP);
        mainLayout.setID("MainLayout");

        x = (Graphic.SCREEN_WIDTH - width) / 2;
        y = (Graphic.SCREEN_HEIGHT - height) / 2;

        closeInterfaceButton = new Button("CLOSE_BUTTON", 0, 0, (int) Graphic.SCREEN_WIDTH / 32f, (int) Graphic.SCREEN_WIDTH / 32f, TEXTURES.closeButton, TEXTURES.closeButton);
        closeInterfaceButton.setSizeInBlocks(0.5f, 0.5f);
        closeInterfaceButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });

        Elements.add(closeInterfaceButton);
    }

    public void createFromXml(FileHandle xmlFile) {
        try {
            XmlInterfaceLoader.load(this, xmlFile.readString());
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
        for (InterfaceElement e : mainLayout.getAllElements()) {
            if (!isElementInInterface(e)) Elements.add(e);
        }
        for (InterfaceElement e : Elements) {
            if (e instanceof Slot) {
                if (!isElementInSlotsArray((Slot) e)) slots.add((Slot) e);
            }
        }
    }

    public Interface setHeaderText(String text) {
        actionBar.setText(text);
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


        mainLayout.setOrientation(Layout.Orientation.HORIZONTAL);
        mainLayout.setGravity(Layout.Gravity.LEFT);

        inventoryLayout.setGravity(Layout.Gravity.TOP);
        inventoryLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        inventoryLayout.setSizeInBlocks(7.5f, 7f);
        inventoryLayout.generateBackground(false);
        inventoryLayout.setTranslationX(1);
        inventoryLayout.setID("inventoryLayout");

        switchTabLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        switchTabLayout.setGravity(Layout.Gravity.LEFT);
        switchTabLayout.setTranslationY(-0.25f);
        switchTabLayout.setID("switchTabLayout");

        slotsLayout.setSize(Layout.Size.WRAP_CONTENT, Layout.Size.WRAP_CONTENT);
        slotsLayout.setGravity(Layout.Gravity.TOP);
        slotsLayout.setTranslationY(-0.25f);
        slotsLayout.setID("slotsLayout");

        optionalLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        optionalLayout.setSizeInBlocks(6f, 7f);
        optionalLayout.setGravity(Layout.Gravity.TOP);
        optionalLayout.generateBackground(false);
        optionalLayout.setTranslationX(0.5f);
        optionalLayout.setID("optionalLayout");

        previousTabInv = new Button("PREVIOUS_INVENTORY_TAB_BUTTON", 0, 0, 0, 0);
        previousTabInv.setIcon(TEXTURES.icon_left);
        nextTabInv = new Button("NEXT_INVENTORY_TAB_BUTTON", 0, 0, 0, 0);
        nextTabInv.setIcon(TEXTURES.icon_right);
        previousTabInv.setSizeInBlocks(1, 1);
        nextTabInv.setSizeInBlocks(1, 1);
        Text backpackText = new Text();
        backpackText.setSizeInBlocks(5, 1);
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
                Slot s = new Slot(id, this);
                s.setSizeInBlocks(1, 1);
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

                    @Override
                    public boolean possibleToDrag() {
                        return true;
                    }
                });
                l.addElements(s);
            }
            slotsLayout.addElements(l);
        }
        mainLayout.addElements(inventoryLayout, optionalLayout);
        updateElementsList();
    }

    public void open(int x, int y, int z) {
        isOpen = true;
        currentItemInv = -1;
        currX = x;
        currY = y;
        currZ = z;
        onOpen();
        onOpen(MAPS.getTile(x, y, z));
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
        System.out.println(a.isInventorySlot);
        System.out.println(a1.isInventorySlot);
        if (a.isInventorySlot) {
            if (!a1.isInventorySlot && a1.id == 0) {
                MAPS.getTile(currX, currY, currZ).getContainer(a1.ID).setItem(a.id, a.count, a.data);
                System.out.println(PLAYER.inventory.containers.size());
                PLAYER.inventory.containers.remove(PLAYER.inventory.containers.get(a.inventorySlot));
            }
        } else {
            if (a1.isInventorySlot) {
                System.out.println("dsdsds");
                PLAYER.inventory.addItem(a.id, a.count);
                MAPS.getTile(currX, currY, currZ).getContainer(a.ID).setItem(0, 0, 0);
            } else {
                MAPS.getTile(currX, currY, currZ).getContainer(a.ID).setItem(a1.id, a1.count, a1.data);
                MAPS.getTile(currX, currY, currZ).getContainer(a1.ID).setItem(temp, temp1, temp2);
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
                for (int j = 0; j < PLAYER.inventory.containers.size(); j++) {
                    if (j >= currentTabInv * 30 && j < currentTabInv * 30 + 30) {
                        Slot slot = getSlot("InventorySlot_" + (j - currentTabInv * 30));
                        slot.inventorySlot = j;
                        slot.id = PLAYER.inventory.containers.get(j).id;
                        slot.count = PLAYER.inventory.containers.get(j).count;
                        slot.data = PLAYER.inventory.containers.get(j).damage;
                    }
                }
                if (nextTabInv.wasClicked) {
                    if (PLAYER.inventory.containers.size() > (currentTabInv + 1) * 30) {
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
                for (int i = 0; i < MAPS.getTile(currX, currY, currZ).containers.size(); i++) {
                    getSlot(MAPS.getTile(currX, currY, currZ).containers.get(i).ID).id = MAPS.getTile(currX, currY, currZ).containers.get(i).getId();
                    getSlot(MAPS.getTile(currX, currY, currZ).containers.get(i).ID).count = MAPS.getTile(currX, currY, currZ).containers.get(i).getCount();
                    getSlot(MAPS.getTile(currX, currY, currZ).containers.get(i).ID).data = MAPS.getTile(currX, currY, currZ).containers.get(i).getDamage();
                }
            }

            mainLayout.setPosition((SCREEN_WIDTH - mainLayout.width) / 2f, (SCREEN_HEIGHT - mainLayout.height - BLOCK_SIZE) / 2f);
            mainLayout.update(INTERFACE_CAMERA);

            tick();
            if (isBlockInterface) tick(MAPS.getTile(currX, currY, currZ));
            actionBar.setSizeInBlocks(mainLayout.BWidth, 1);
            actionBar.setPosition(mainLayout.x, mainLayout.y + mainLayout.height);
            closeInterfaceButton.setPosition(actionBar.x + actionBar.width - BLOCK_SIZE, actionBar.y + BLOCK_SIZE * 0.25f);
            actionBar.update(INTERFACE_CAMERA);
            closeInterfaceButton.update(INTERFACE_CAMERA);
        }
    }

    public void render() {
        if (isOpen) {
            mainLayout.render(BATCH, INTERFACE_CAMERA);

            actionBar.render(BATCH, INTERFACE_CAMERA);

            preRender();

            closeInterfaceButton.render(BATCH, INTERFACE_CAMERA);

            for (Slot slot : slots) {
                if (slot.isInventorySlot) {
                    if (slot.inventorySlot < PLAYER.inventory.containers.size())
                        slot.itemRender(PLAYER.inventory.containers.get(slot.inventorySlot));
                } else slot.itemRender(null);
            }
            postRender();
        }
    }

    public void resize() {
        actionBar.setSize(SCREEN_WIDTH, BLOCK_SIZE);
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 1);
        mainLayout.resize();
        closeInterfaceButton.resize();
        onOpen();
    }

    public void tick() {
    }

    public void tick(Tile tile) {
    }

    public void onOpen() {
        mainLayout.hide(false);
        for (InterfaceElement e : Elements) {
            e.hide(false);
        }
    }

    public void onOpen(Tile tile) {

    }

    public void onClose() {
        mainLayout.hide(true);
        for (InterfaceElement e : Elements) {
            e.hide(true);
        }
    }

    public void preRender() {
    }

    public void postRender() {
    }
}