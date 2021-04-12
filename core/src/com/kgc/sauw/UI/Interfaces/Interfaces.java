package com.kgc.sauw.UI.Interfaces;

import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;

public final class Interfaces {
    public static final GameInterface GAME_INTERFACE;
    public static final ConsoleInterface CONSOLE_INTERFACE;
    private static final ArrayList<Interface> INTERFACES;
    static {
        GAME_INTERFACE = new GameInterface();
        CONSOLE_INTERFACE = new ConsoleInterface();
        INTERFACES = new ArrayList<>();
        addInterface(CONSOLE_INTERFACE);
    }
    public static void addInterface(Interface Interface){
        INTERFACES.add(Interface);
    }
    public static void updateInterfaces(Player pl, Camera2D cam){
        for(Interface i : INTERFACES) i.update(pl, cam);
    }
    public static void renderInterfaces(Player pl, Camera2D cam){
        for(Interface i : INTERFACES) i.render(pl, cam);
    }
    public static boolean isAnyInterfaceOpen() {
        for(Interface i : INTERFACES){
            if(i.isOpen) return true;
        }
        return false;
    }
}
