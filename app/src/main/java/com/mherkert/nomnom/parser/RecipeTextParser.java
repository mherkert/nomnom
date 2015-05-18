package com.mherkert.nomnom.parser;

import com.mherkert.nomnom.domain.ParseState;
import com.mherkert.nomnom.domain.Recipe;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class RecipeTextParser {

    Recipe toDomain(String text){
        StringTokenizer st = new StringTokenizer(text, "\n");

        RecipeBuilder builder = new RecipeBuilder();
        while (st.hasMoreTokens()) {
            builder.add(st.nextToken().trim());
        }
        builder.done();

        return builder.build();
    }

    private class RecipeBuilder
    {
        private ParseState state = ParseState.NEW;

        String title;
        String description;
        List<String> ingredients = new LinkedList<>();
        List<String> instructions = new LinkedList<>();

        Pattern ingredientPattern = Pattern.compile("\\^(\\d).{1,100}\\$");
        Pattern instructionPattern = Pattern.compile("\\^.{101,}\\$");

        void done() {
            state = ParseState.DONE;
        }

        Recipe build(){
            return new Recipe(title, description, ingredients, instructions);
        }

        void add(String text) {
            switch (state) {
                case NEW:
                    title = text;
                    state = ParseState.READING_DESCRIPTION;
                    break;
                case READING_DESCRIPTION:
                    if (ingredientPattern.matcher(text).matches()) {
                        ingredients.add(text);
                        state = ParseState.READING_INGREDIENTS;
                    } else
                        description = text;
                    break;
                case READING_INGREDIENTS:
                    if (instructionPattern.matcher(text).matches()) {
                        instructions.add(text);
                        state = ParseState.READING_INSTRUCTIONS;
                    } else
                        ingredients.add(text);
                    break;
                case READING_INSTRUCTIONS:
                    instructions.add(text);
                    break;
                case DONE:
                    break;
                default:
                    break;
            }
        }
    }
}
