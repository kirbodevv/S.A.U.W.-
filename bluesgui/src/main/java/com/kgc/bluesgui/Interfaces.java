package com.kgc.bluesgui;

import java.util.ArrayList;

public class Interfaces {
    public static ArrayList<Interface> interfaces = new ArrayList<>();

    public static void resize() {
        for (Interface i : interfaces) i.resize();
    }
}
