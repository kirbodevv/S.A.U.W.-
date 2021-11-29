package com.kgc.sauw.core.recipes;

import com.kgc.sauw.core.register.Registry;

public class Recipes extends Registry<Recipe> {
    public static final Recipes INSTANCE = new Recipes();

    @Override
    public String getIDGroup() {
        return "recipe";
    }
}
