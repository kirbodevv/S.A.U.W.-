package com.kgc.sauw.core.recipes;

import com.kgc.sauw.core.registry.RegistryObject;

import java.util.ArrayList;

public class Recipe extends RegistryObject {
    protected ArrayList<Element> ingredients = new ArrayList<>();
    protected Element result;

    public void addIngredient(String package_, String id, int count) {
        this.ingredients.add(new Element(package_ + ":" + id, count));
    }

    public void setResult(String fullId, int resultCount) {
        result = new Element(fullId, resultCount);
    }

    public ArrayList<Element> getIngredients() {
        return ingredients;
    }

    public Element getResult() {
        return result;
    }

    @Override
    public void init() {

    }

    public static class Element {
        public String fullId;
        public int count;

        public Element(String fullId, int count) {
            this.fullId = fullId;
            this.count = count;
        }
    }
}
