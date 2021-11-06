package com.kgc.sauw.core.gui;

import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.skins.Skins;
import com.kgc.sauw.core.utils.Resource;

import java.util.ArrayList;

import static com.kgc.sauw.core.graphic.Graphic.*;

public class Interface {
    public String id;

    public boolean isOpen = false;
    public float width, height, x, y;

    public ArrayList<InterfaceElement> elements = new ArrayList<>();
    public ArrayList<Slot> slots = new ArrayList<>();

    public final TextView actionBar = new TextView();
    public Layout mainLayout;
    public Button closeInterfaceButton;

    private InterfaceController controller;

    public Interface() {
        mainLayout = new Layout(Layout.Orientation.VERTICAL);

        width = Graphic.SCREEN_WIDTH;
        height = Graphic.SCREEN_HEIGHT;

        actionBar.setSize(SCREEN_WIDTH, BLOCK_SIZE);

        mainLayout.setBackground(Skins.interface_background);
        mainLayout.setSize(Layout.Size.FIXED_SIZE, Layout.Size.FIXED_SIZE);
        mainLayout.setSizeInBlocks(16, SCREEN_HEIGHT / BLOCK_SIZE - 1);
        mainLayout.setGravity(Layout.Gravity.TOP);
        mainLayout.setId("MainLayout");

        x = (Graphic.SCREEN_WIDTH - width) / 2;
        y = (Graphic.SCREEN_HEIGHT - height) / 2;

        closeInterfaceButton = new Button("CLOSE_BUTTON", 0, 0, 0, 0);
        closeInterfaceButton.setIcon(Resource.getTexture("Interface/closeButton.png"));
        closeInterfaceButton.setSizeInBlocks(0.75f, 0.75f);
        closeInterfaceButton.addEventListener(new OnClickListener() {
            @Override
            public void onClick() {
                close();
            }
        });

        elements.add(closeInterfaceButton);
    }

    public void setController(InterfaceController controller) {
        this.controller = controller;
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
            if (slot.id.equals(ID)) {
                return slot;
            }
        }
        return null;
    }

    public InterfaceElement getElementByFullId(String fullId) {
        for (InterfaceElement element : elements) {
            if (element.id.equals(fullId)) {
                return element;
            }
        }
        return null;
    }

    public InterfaceElement getElement(String elementId) {
        return getElementByFullId(id + "." + elementId);
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

    public void tick() {

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