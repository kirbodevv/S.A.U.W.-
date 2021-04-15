package com.kgc.sauw.UI.Interfaces;

import com.kgc.sauw.UI.GameInterface;
import com.kgc.sauw.UI.Interface;
import com.kgc.sauw.UI.Interfaces.blockInterfaces.ChestInterface;
import com.kgc.sauw.entity.Player;
import com.kgc.sauw.utils.Camera2D;

import java.util.ArrayList;

public final class Interfaces {
    public static final GameInterface GAME_INTERFACE;
    /**S.A.U.W. Interfaces**/
    public static final CraftingInterface CRAFTING_INTERFACE;
    public static final ConsoleInterface CONSOLE_INTERFACE;
    public static final PauseInterface PAUSE_INTERFACE;
    public static final DeadInterface DEAD_INTERFACE;
    public static final InventoryInterface INVENTORY_INTERFACE;
    /**BLOCK Interfaces**/
    public static final ChestInterface CHEST_INTERFACE;


    private static final ArrayList<Interface> INTERFACES;
    static {
        GAME_INTERFACE = new GameInterface();

        CONSOLE_INTERFACE = new ConsoleInterface();
        CRAFTING_INTERFACE = new CraftingInterface();
        PAUSE_INTERFACE = new PauseInterface();
        DEAD_INTERFACE = new DeadInterface();
        INVENTORY_INTERFACE = new InventoryInterface();

        CHEST_INTERFACE = new ChestInterface();

        INTERFACES = new ArrayList<>();

        addInterface(CONSOLE_INTERFACE);
        addInterface(CRAFTING_INTERFACE);
        addInterface(PAUSE_INTERFACE);
        addInterface(DEAD_INTERFACE);
        addInterface(INVENTORY_INTERFACE);

        addInterface(CHEST_INTERFACE);
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
