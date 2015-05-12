package com.mherkert.nomnom.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mherkert.nomnom.R;
import com.mherkert.nomnom.domain.Recipe;

public class RecipeFragment extends Fragment {
    private static final String TAG = RecipeFragment.class.getSimpleName();

    private LinearLayout mLinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLinearLayout = (LinearLayout) getActivity().findViewById(R.id.recipe_container);
    }

    public void updateRecipeDisplay(Context context, Recipe recipe) {
        Log.d(TAG, "Display recipe '" + recipe + "'");
        mLinearLayout.setBackgroundColor(Color.parseColor(recipe.getMeta().getColor()));
        mLinearLayout.addView(RecipeViewFactory.getTitleView(context, recipe.getTitle()));
        if (recipe.getDescription() != null && !recipe.getDescription().isEmpty())
            mLinearLayout.addView(RecipeViewFactory.getDescriptionView(context, recipe.getDescription()));

        mLinearLayout.addView(RecipeViewFactory.getDividerView(getLayoutInflater(null))); // TODO really? null?
        for (String ingredient : recipe.getIngredients()) {
            mLinearLayout.addView(RecipeViewFactory.getIngredientView(context, ingredient));
        }
        mLinearLayout.addView(RecipeViewFactory.getDividerView(getLayoutInflater(null)));

        for (String instruction : recipe.getInstructions()) {
            mLinearLayout.addView(RecipeViewFactory.getInstructionView(context, instruction));
        }
    }
}
