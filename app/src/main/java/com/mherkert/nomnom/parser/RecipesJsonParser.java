package com.mherkert.nomnom.parser;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mherkert.nomnom.domain.Recipe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipesJsonParser {

    public List<Recipe> toDomain(String text) {
        Type listType = new TypeToken<ArrayList<Recipe>>() {
        }.getType();
        List<Recipe> recipes = new GsonBuilder().create().fromJson(text, listType);
        if (recipes == null)
            return new ArrayList<>();
        return recipes;
    }

    public String toJson(List<Recipe> recipes) {
        return new GsonBuilder().create().toJson(recipes);
    }
}
