package com.kgc.sauw.core.gui;

import com.badlogic.gdx.files.FileHandle;
import com.kgc.sauw.core.Container;
import com.kgc.sauw.core.gui.elements.Slot;

import java.util.ArrayList;

public class InterfaceUtils {
    public static void sendToSlot(Slot slot1, Slot slot2) {
        if (slot2.SF == null || slot2.SF.isValid(slot1.getContainer(), slot1.ID)) {
            InterfaceUtils.swap(slot1.getContainer(), slot2.getContainer());
        }

        if (slot1.SF != null) slot1.SF.onItemSwapping(slot1.getContainer());
        if (slot2.SF != null) slot2.SF.onItemSwapping(slot1.getContainer());
    }

    public static void swap(Container container, Container container1) {
        Container temp = new Container(container);
        container.setItemFromContainer(container1);
        container1.setItemFromContainer(temp);
    }

    public static boolean isElementInInterface(InterfaceElement e, ArrayList<InterfaceElement> elements) {
        for (InterfaceElement e1 : elements) {
            if (e1.equals(e)) return true;
        }
        return false;
    }

    public static boolean isElementInSlotsArray(Slot slot, ArrayList<Slot> slots) {
        for (Slot slot1 : slots) {
            if (slot1.equals(slot)) return true;
        }
        return false;
    }

    public static void createFromXml(FileHandle xmlFile, Interface interface_) {
        try {
            XmlInterfaceLoader.load(interface_, xmlFile.readString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createInventory(Interface interface_) {
        InventoryFragment fragment = new InventoryFragment();
        fragment.createInventory(interface_);
        interface_.mainLayout.addElements(fragment);
        interface_.updateElementsList();
    }
}
