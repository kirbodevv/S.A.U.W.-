package com.kgc.sauw.modding;

import java.util.ArrayList;

import static com.kgc.sauw.gui.interfaces.Interfaces.GAME_INTERFACE;

public class Console {
    public ArrayList<String> inputs = new ArrayList<String>();

    public void print(String txt) {
        GAME_INTERFACE.consolePrint(txt);
    }

    public ArrayList<String> input() {
        return inputs;
    }

    public String input(int pointer) {
        return inputs.get(pointer);
    }
}
