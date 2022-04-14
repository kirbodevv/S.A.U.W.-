package com.kgc.sauw.core.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.jvmfrog.curve.registry.RegistryObject;
import com.kgc.sauw.core.graphic.Graphic;
import com.kgc.sauw.core.gui.elements.Button;
import com.kgc.sauw.core.gui.elements.Layout;
import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.gui.elements.TextView;
import com.kgc.sauw.core.resource.Resource;
import com.kgc.sauw.core.skins.Skins;

import java.util.ArrayList;

import static com.kgc.sauw.core.graphic.Graphic.*;

public class Interface extends RegistryObject {
    public String id;

    public boolean isOpen = false;
    public float width, height, x, y;

    public ArrayList<InterfaceElement> elements = new ArrayList<>();
    public ArrayList<Slot> slots = new ArrayList<>();

    public final TextView actionBar = new TextView();
    public Layout mainLayout;
    public Button closeInterfaceButton;

    private InterfaceController controller;

    private final Color batchColor = new Color();
    private Color color = new Color(0xFFFFFFFF);

    private boolean closeButtonHidden;

    public void closeButtonHidden(boolean closeButtonHidden) {
        this.closeButtonHidden = closeButtonHidden;
    }

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
        closeInterfaceButton.setIcon(Resource.getTexture("interface/closeButton.png"));
        closeInterfaceButton.setSizeInBlocks(0.75f, 0.75f);
        closeInterfaceButton.addEventListener(this::close);

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
        mainLayout.setPosition((SCREEN_WIDTH - mainLayout.width) / 2f,
                (SCREEN_HEIGHT - mainLayout.height - BLOCK_SIZE) / 2f - BLOCK_SIZE * 2);
        color.set(1, 1, 1, 0);
        onOpen();
        mainLayout.hide(false);
        for (InterfaceElement element : elements) {
            element.hide(false);
        }
    }

    public void close() {
        isOpen = false;
        onClose();
        color.set(1, 1, 1, 1);
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

            mainLayout.setPosition(
                    MathUtils.lerp(mainLayout.x, (SCREEN_WIDTH - mainLayout.width) / 2f, 0.1f),
                    MathUtils.lerp(mainLayout.y, (SCREEN_HEIGHT - mainLayout.height - BLOCK_SIZE) / 2f, 0.1f));
            mainLayout.updatePosition();
            mainLayout.update(INTERFACE_CAMERA);
            closeInterfaceButton.hide(closeButtonHidden);
            actionBar.setSizeInBlocks(mainLayout.bWidth, 1);
            actionBar.setPosition(mainLayout.x, mainLayout.y + mainLayout.height);
            closeInterfaceButton.setPosition(actionBar.x + actionBar.width - BLOCK_SIZE, actionBar.y + BLOCK_SIZE * 0.125f);
            actionBar.update(INTERFACE_CAMERA);
            closeInterfaceButton.update(INTERFACE_CAMERA);
        }
    }

    public void render() {
        if (isOpen) {
            batchColor.set(BATCH.getColor());
            COLOR_ALPHA = MathUtils.lerp(color.a, 1f, 0.1f);
            color.set(1f, 1f, 1f, COLOR_ALPHA);
            BATCH.setColor(color);
            preRender();
            mainLayout.render(BATCH, INTERFACE_CAMERA);

            actionBar.render(BATCH, INTERFACE_CAMERA);

            closeInterfaceButton.render(BATCH, INTERFACE_CAMERA);
            for (Slot slot : slots) {
                slot.itemRender();
            }
            postRender();
            BATCH.setColor(batchColor);
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

    @Override
    public void init() {
    }
}