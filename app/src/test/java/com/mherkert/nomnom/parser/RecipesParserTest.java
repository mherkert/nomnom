package com.mherkert.nomnom.parser;

import com.mherkert.nomnom.domain.Meta;
import com.mherkert.nomnom.domain.Recipe;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipesParserTest {

    RecipesParser recipesParser;

    private final String jsonRecipes = "[{\"title\":\"title1\",\"ingredients\":[\"ingredient_11\",\"ingredient_12\",\"ingredient_13\"],\"instructions\":[\"instruction_11\",\"instruction_12\"],\"meta\":{\"color\":\"color1\"}},{\"title\":\"title2\",\"description\":\"description2\",\"ingredients\":[\"ingredient_21\",\"ingredient_22\",\"ingredient_23\"],\"meta\":{\"color\":\"color2\"}}]";

    @Before
    public void setUp() throws Exception {
        recipesParser = new RecipesParser();
    }

    @Test
    public void toDomain() {
        List<Recipe> recipes = recipesParser.toDomain(jsonRecipes);
        Assert.assertEquals(2, recipes.size());

        Assert.assertEquals("title1", recipes.get(0).getTitle());
        Assert.assertEquals(null, recipes.get(0).getDescription());
        Assert.assertEquals("ingredient_11", recipes.get(0).getIngredients().get(0));
        Assert.assertEquals("ingredient_12", recipes.get(0).getIngredients().get(1));
        Assert.assertEquals("ingredient_13", recipes.get(0).getIngredients().get(2));
        Assert.assertEquals("instruction_11", recipes.get(0).getInstructions().get(0));
        Assert.assertEquals("instruction_12", recipes.get(0).getInstructions().get(1));
        Assert.assertEquals("color1", recipes.get(0).getMeta().getColor());

        Assert.assertEquals("title2", recipes.get(1).getTitle());
        Assert.assertEquals("description2", recipes.get(1).getDescription());
        Assert.assertEquals("ingredient_21", recipes.get(1).getIngredients().get(0));
        Assert.assertEquals("ingredient_22", recipes.get(1).getIngredients().get(1));
        Assert.assertEquals("ingredient_23", recipes.get(1).getIngredients().get(2));
        Assert.assertEquals(null, recipes.get(1).getInstructions());
        Assert.assertEquals("color2", recipes.get(1).getMeta().getColor());
    }

    @Test
    public void toDomainGivenEmpty() {
        List<Recipe> recipes = recipesParser.toDomain("");
        Assert.assertTrue(recipes.isEmpty());
        recipes = recipesParser.toDomain("[]");
        Assert.assertTrue(recipes.isEmpty());
        recipes = recipesParser.toDomain(null);
        Assert.assertTrue(recipes.isEmpty());
    }

    @Test
    public void toJson() {
        List<Recipe> recipes = new ArrayList<>(2);
        Recipe recipe1 = new Recipe("title1", null, Arrays.asList("ingredient_11", "ingredient_12", "ingredient_13"), Arrays.asList("instruction_11", "instruction_12"), new Meta("color1"));
        Recipe recipe2 = new Recipe("title2", "description2", Arrays.asList("ingredient_21", "ingredient_22", "ingredient_23"), null, new Meta("color2"));
        recipes.add(recipe1);
        recipes.add(recipe2);

        String json = recipesParser.toJson(recipes);
        System.out.println("json = " + json);
        Assert.assertEquals(jsonRecipes, json);
    }

    @Test
    public void toJsonGivenEmpty(){
        String json = recipesParser.toJson(new ArrayList<Recipe>());
        Assert.assertEquals("[]", json);
    }
}