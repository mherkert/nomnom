package com.mherkert.nomnom.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.mherkert.nomnom.R;
import com.mherkert.nomnom.domain.Meta;
import com.mherkert.nomnom.domain.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {

    private static final String TAG = RecipesFragment.class.getSimpleName();

    private RecipeViewAdapter mAdapter;
    private List<Recipe> mRecipes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipes, container, false);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO retain data

        String text = loadData(R.raw.recipes);
        try {
            mRecipes = parseRecipes(text);
        } catch (JSONException e) {
            Log.i(TAG, "JSONException");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridView gridView = (GridView) getActivity().findViewById(R.id.recipesGrid);

        mAdapter = new RecipeViewAdapter(getActivity(), mRecipes);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View v,
                                    int position, long id) {

                Toast.makeText(getActivity(), "TODO: Open Recipe View", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String loadData(int id) {
        InputStream inputStream = getActivity().getResources().openRawResource(id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();

        try {

            String line = "";
            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

        } catch (IOException e) {
            Log.i(TAG, "IOException");
        }

        return builder.toString();
    }

    private List<Recipe> parseRecipes(String text) throws JSONException {

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
}
