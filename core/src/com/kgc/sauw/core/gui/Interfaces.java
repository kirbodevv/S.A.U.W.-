package com.kgc.sauw.core.gui;

import com.kgc.sauw.core.gui.elements.Slot;
import com.kgc.sauw.core.registry.Registry;
import com.kgc.sauw.game.gui.HUD;

import static com.kgc.sauw.core.graphic.Graphic.BATCH;
import static com.kgc.sauw.core.graphic.Graphic.INTERFACE_CAMERA;

public class Interfaces {
    public static final Registry<Interface> registry = new Registry<>("interface");

    public static final HUD HUD;

    static {
        HUD = new HUD();
    }

    public static void renderInterfaces() {
        Slot.itemDamageProgressBar.hide(true);
        for (Interface i : registry.getObjects()) i.render();
        Slot.itemDamageProgressBar.update(INTERFACE_CAMERA);
        Slot.itemDamageProgressBar.render(BATCH, INTERFACE_CAMERA);
    }

    public static boolean isAnyInterfaceOpen() {
        for (Interface i : registry.getObjects()) {
            if (i.isOpen) return true;
        }
        return false;
    }

    public static void closeInterface() {
        for (Interface i : registry.getObjects()) {
            if (i.isOpen) i.close();
        }
    }

    public static void updateInterfaces() {
        for (Interface i : registry.getObjects()) i.update();
    }
}
