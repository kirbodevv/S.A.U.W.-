package com.kgc.sauw.core.gui;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.gui.elements.Text;
import com.kgc.sauw.core.utils.Resource;
import com.kgc.sauw.game.skins.Skins;

import java.util.ArrayList;

import static com.kgc.sauw.core.graphic.Graphic.*;

public abstract class Interface {
    public String ID;

    public boolean isOpen = false;
    public float width, height, x, y;


    public ArrayList<InterfaceElement> elements = new ArrayList<>();
    public ArrayList<Slot> slots = new ArrayList<>();


    private final Text actionBar;
    public Layout mainLayout;
    public Button closeInterfaceButton;

    public Interface(String ID) {
        this.ID = ID;

        mainLayout = new Layout(Layout.Orientation.VERTICAL);

        width = Graphic.SCREEN_WIDTH;
        height = Graphic.SCREEN_HEIGHT;

        actionBar = new Text();
        actionBar.setSize(SCREEN_WIDTH, BLOCK_SIZE);

        mainLayout.setBackground(Skins.interface_background);
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 1);
        mainLayout.setGravity(Layout.Gravity.TOP);
        mainLayout.setID("MainLayout");

        x = (Graphic.SCREEN_WIDTH - width) / 2;
        y = (Graphic.SCREEN_HEIGHT - height) / 2;

        closeInterfaceButton = new Button("CLOSE_BUTTON", 0, 0, 0, 0);
        closeInterfaceButton.setIcon(Resource.getTexture("Interface/closeButton.png"));
        closeInterfaceButton.setSizeInBlocks(0.75f, 0.75f);
        closeInterfaceButton.addEventListener(new Button.EventListener() {
            @Override
            public void onClick() {
                close();
            }
        });

        elements.add(closeInterfaceButton);
    }

    public void createFromXml(FileHandle xmlFile) {
        try {
            XmlInterfaceLoader.load(this, xmlFile.readString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateElementsList() {
        for (InterfaceElement e : mainLayout.getAllElements()) {
            if (!InterfaceUtils.isElementInInterface(e, elements)) elements.add(e);
        }
        for (InterfaceElement e : elements) {
            if (e instanceof Slot) {
                if (!InterfaceUtils.isElementInSlotsArray((Slot) e, slots)) slots.add((Slot) e);
            }
        }
    }

    public void createInventory() {
        InventoryFragment fragment = new InventoryFragment();
        fragment.createInventory(this);
        mainLayout.addElements(fragment);
        updateElementsList();
    }

    public void setHeaderText(String text) {
        actionBar.setText(text);
    }

    public void open() {
        isOpen = true;

        onOpen();
        mainLayout.hide(false);
        for (InterfaceElement element : elements) {
            element.hide(false);
        }
    }

    public void close() {
        isOpen = false;
        onClose();
        mainLayout.hide(true);
        for (InterfaceElement element : elements) {
            element.hide(true);
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
        for (InterfaceElement element : elements) {
            if (element.ID.equals(ID)) {
                return element;
            }
        }
        return null;
    }

    public void update() {
        if (isOpen) {
            for (Slot slot : slots) slot.setContainer(null);

            tick();

            mainLayout.setPosition((SCREEN_WIDTH - mainLayout.width) / 2f, (SCREEN_HEIGHT - mainLayout.height - BLOCK_SIZE) / 2f);
            mainLayout.update(INTERFACE_CAMERA);

            actionBar.setSizeInBlocks(mainLayout.BWidth, 1);
            actionBar.setPosition(mainLayout.x, mainLayout.y + mainLayout.height);
            closeInterfaceButton.setPosition(actionBar.x + actionBar.width - BLOCK_SIZE, actionBar.y + BLOCK_SIZE * 0.125f);
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
                slot.itemRender();
            }
            postRender();
        }
    }

    public void resize() {
        mainLayout.resize();
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 1);
        mainLayout.setPosition((SCREEN_WIDTH - mainLayout.width) / 2f, (SCREEN_HEIGHT - mainLayout.height - BLOCK_SIZE) / 2f);
        actionBar.setSize(SCREEN_WIDTH, BLOCK_SIZE);
        closeInterfaceButton.resize();
    }

    public abstract void tick();

    public abstract void onOpen();

    public abstract void onClose();

    public abstract void preRender();

    public abstract void postRender();
}