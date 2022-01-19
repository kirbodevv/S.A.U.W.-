package com.kgc.sauw.game.generated;

import com.kgc.sauw.core.recipes.Recipe;
import com.kgc.sauw.core.recipes.Recipes;
import com.kgc.sauw.core.recipes.ToolCraftRecipe;

public class RecipesGenerated {
    /*it is not generated file at the moment*/
    public static void init() {
        Recipe recipe = new ToolCraftRecipe();
        recipe.addIngredient("sauw", "item:handsaw", 1);
        recipe.addIngredient("sauw", "item:log", 1);
        recipe.setResult("sauw:item:planks", 2);
        Recipes.registry.register(recipe, "sauw", "planks");
        recipe = new ToolCraftRecipe();
        recipe.addIngredient("sauw", "item:hammer", 1);
        recipe.addIngredient("sauw", "item:iron_ingot", 1);
        recipe.setResult("sauw:item:iron_plate", 1);
        Recipes.registry.register(recipe, "sauw", "iron_plate");
    }
}
