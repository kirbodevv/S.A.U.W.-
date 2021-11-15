package com.kgc.sauw.core.environment.recipes;

import static com.kgc.sauw.core.GameContext.SAUW;

public class CraftRecipe extends Recipe {
    public CraftRecipe(String id, String result, int resultCount) {
        super(id);

        this.result[0] = SAUW.getId(result);
        this.result[1] = resultCount;
    }
}
