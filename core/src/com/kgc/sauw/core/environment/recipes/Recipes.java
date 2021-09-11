package com.kgc.sauw.core.environment.recipes;

import java.util.ArrayList;

public class Recipes {
    private static final ArrayList<Recipe> recipes = new ArrayList<>();

    public static void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public static Recipe getRecipe(String id) {
        for (Recipe recipe : recipes) {
            if (recipe.stringID.equals(id))
                return recipe;
        }
        return null;
    }
}
