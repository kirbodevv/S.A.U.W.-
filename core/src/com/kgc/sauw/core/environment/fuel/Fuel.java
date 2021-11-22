package com.kgc.sauw.core.environment.fuel;

import java.util.ArrayList;

import static com.kgc.sauw.core.GameContext.SAUW;

public class Fuel {
    private static ArrayList<Fuel> fuels = new ArrayList<>();

    static {
        addFuel(new Fuel(SAUW.getId("item:log"), 100));
    }

    public static void addFuel(Fuel fuel) {
        fuels.add(fuel);
    }

    public static boolean compareFuel(int id) {
        for (Fuel fuel : fuels) if (fuel.id == id) return true;
        return false;
    }

    public static Fuel getFuel(int id) {
        for (Fuel fuel : fuels) {
            if (fuel.id == id)
                return fuel;
        }
        return null;
    }

    public int id;
    public int duration;

    public Fuel(int id, int duration) {
        this.id = id;
        this.duration = duration;
    }
}
