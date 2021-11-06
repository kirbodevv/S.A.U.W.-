package com.kgc.sauw.core.mod;

import java.util.ArrayList;

import static com.kgc.sauw.game.gui.Interfaces.HUD;

public class Console {
    public ArrayList<String> inputs = new ArrayList<>();

    public void print(String txt) {
        /*Починить вывод в консоль*/
    }

    public ArrayList<String> input() {
        return inputs;
    }

    public String input(int pointer) {
        return inputs.get(pointer);
    }
}
