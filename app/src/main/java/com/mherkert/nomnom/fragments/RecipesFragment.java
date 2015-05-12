package com.mherkert.nomnom.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.mherkert.nomnom.R;
import com.mherkert.nomnom.domain.Recipes;

public class RecipesFragment extends Fragment {

    private static final String TAG = RecipesFragment.class.getSimpleName();

    private static final String RECIPES_KEY = "RECIPES";

    public interface RecipeFragmentCallbacks {
        void onRecipeItemSelected(int position);
    }

    private Recipes mRecipes;
    private RecipeFragmentCallbacks mCallback;

    public static RecipesFragment newInstance(Recipes recipes) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPES_KEY, recipes);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = getArguments();
        mRecipes = (Recipes) data.getSerializable(RECIPES_KEY);
        return inflater.inflate(R.layout.fragment_recipes, container, false);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StaggeredGridView gridView = (StaggeredGridView) getActivity().findViewById(R.id.recipesGrid);

        RecipeViewAdapter mAdapter = new RecipeViewAdapter(getActivity(), mRecipes.getRecipes());
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View v,
                                    int position, long id) {

                mCallback.onRecipeItemSelected(position);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (RecipeFragmentCallbacks) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement RecipeFragmentCallbacks");
        }
    }
}
