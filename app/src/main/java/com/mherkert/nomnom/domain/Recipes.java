package com.mherkert.nomnom.domain;

import java.io.Serializable;
import java.util.List;

public class Recipes implements Serializable {
    List<Recipe> recipes;

    public Recipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
