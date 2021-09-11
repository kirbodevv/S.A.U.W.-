package com.kgc.sauw.core.environment.recipes;

import com.kgc.sauw.core.utils.ID;

import java.util.ArrayList;

public class Recipe {
    public final int id;
    public final String stringID;
    protected ArrayList<Integer[]> ingredients = new ArrayList<>();
    protected int[] result = new int[2];

    public Recipe(String id) {
        this.id = ID.registeredId(id);
        this.stringID = id;
    }

    public void addIngredient(String id, int count) {
        this.ingredients.add(new Integer[]{ID.get(id), count});
    }
}
