package com.kgc.sauw.ui.elements;

import com.kgc.sauw.ui.InterfaceElement;

import java.util.ArrayList;

public final class Elements {
    public final static ArrayList<InterfaceElement> UI_ELEMENTS;

    static {
        UI_ELEMENTS = new ArrayList<>();
    }

    public static void addElement(InterfaceElement Element) {
        UI_ELEMENTS.add(Element);
    }

    public static void dispose() {
        for (InterfaceElement e : UI_ELEMENTS) {
            e.dispose();
        }
    }

    public static InterfaceElement getElementById(String id) {
        for (InterfaceElement e : UI_ELEMENTS) {
            if (e.ID.equals(id)) return e;
        }
        return null;
    }
}
