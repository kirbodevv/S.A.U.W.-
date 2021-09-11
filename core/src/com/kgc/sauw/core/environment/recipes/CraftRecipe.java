package com.kgc.sauw.core.environment.recipes;

import com.kgc.sauw.core.utils.ID;

public class CraftRecipe extends Recipe {
    public CraftRecipe(String id, String result, int resultCount) {
        super(id);

        this.result[0] = ID.get(result);
        this.result[1] = resultCount;
    }
}
