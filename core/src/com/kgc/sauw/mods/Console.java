package com.kgc.sauw.mods;

import java.util.ArrayList;

import static com.kgc.sauw.game.gui.Interfaces.HUD;

public class Console {
    public ArrayList<String> inputs = new ArrayList<String>();

    public void print(String txt) {
        HUD.consolePrint(txt);
    }

    public ArrayList<String> input() {
        return inputs;
    }

    public String input(int pointer) {
        return inputs.get(pointer);
    }
}
