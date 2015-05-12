package com.mherkert.nomnom.parser;

import com.mherkert.nomnom.domain.Meta;
import com.mherkert.nomnom.domain.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipesParser {

    public List<Recipe> toDomain(String text) throws JSONException {

        JSONArray jsonRecipes = new JSONArray(text);
        List<Recipe> recipes = new ArrayList<>(jsonRecipes.length());

        for (int i = 0; i < jsonRecipes.length(); i++) {
            JSONObject recipeObject = (JSONObject) jsonRecipes.get(i);
            String description = recipeObject.optString("description");
            Recipe recipe = new Recipe(recipeObject.getString("title"), description);

            JSONArray ingredients = recipeObject.getJSONArray("ingredients");
            for (int j = 0; j < ingredients.length(); j++) {
                recipe.addIngredient(ingredients.getString(j));
            }
            JSONArray instructions = recipeObject.getJSONArray("instructions");
            for (int j = 0; j < instructions.length(); j++) {
                recipe.addInstruction(instructions.getString(j));
            }
            JSONObject metaObject = recipeObject.getJSONObject("meta");
            recipe.setMeta(new Meta(metaObject.getString("color")));

            recipes.add(recipe);
        }

        return recipes;
    }

    public String toJson(List<Recipe> recipes){
        return null;
    }

}
