package com.kgc.sauw.UI.Interfaces;

import com.kgc.sauw.UI.GameInterface;

public final class Interfaces {
    public static final GameInterface GAME_INTERFACE;
    public static final ConsoleInterface CONSOLE_INTERFACE;
    static {
        GAME_INTERFACE = new GameInterface();
        CONSOLE_INTERFACE = new ConsoleInterface();
    }
}
