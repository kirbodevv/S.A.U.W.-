package com.kgc.sauw.core.environment.recipes;

import java.util.HashMap;

public class Recipes {
    private static final HashMap<String, Recipe> recipes = new HashMap<>();

    public static void addRecipe(Recipe recipe) {
        recipes.put(recipe.stringID, recipe);
    }

    public static Recipe getRecipe(String id) {
        return recipes.get(id);
    }
}
