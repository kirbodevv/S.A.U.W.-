package com.kgc.bluesgui;

import java.util.ArrayList;

public class InterfaceUtils {

    public static boolean isElementInInterface(InterfaceElement e, ArrayList<InterfaceElement> elements) {
        for (InterfaceElement e1 : elements) {
            if (e1.equals(e)) return true;
        }
        return false;
    }
}
