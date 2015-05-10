package com.mherkert.nomnom.domain;

import java.util.LinkedList;
import java.util.List;

public class Recipe {
    String title;
    String description;
    List<String> ingredients;
    List<String> instructions;
    Meta meta;

    public Recipe(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Recipe(String title, String description, List<String> ingredients, List<String> instructions) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public void addIngredient(String ingredient) {
        if (ingredients == null)
            ingredients = new LinkedList<>();
        ingredients.add(ingredient);
    }

    public void addInstruction(String instruction) {
        if (instructions == null)
            instructions = new LinkedList<>();
        instructions.add(instruction);
    }

    public String getTitle() {
        return title;
    }

    public String toHtmlSummary() {
        StringBuilder builder = new StringBuilder();
        builder.append("<h2>");
        builder.append(this.title);
        builder.append("</h2>");
        if (this.description != null) {
            builder.append("<p><i>");
            builder.append(this.description);
            builder.append("</i></p>");
        }
        int i = 0;
        for (String ingredient : ingredients) {
            if (i <= 3) {
                i++;
                builder.append(ingredient);
                builder.append("<br/>");
            } else {
                builder.append("...");
                break;
            }
        }
        return builder.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
