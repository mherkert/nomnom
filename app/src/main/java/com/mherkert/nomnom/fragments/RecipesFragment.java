package com.mherkert.nomnom.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mherkert.nomnom.R;
import com.mherkert.nomnom.domain.Recipes;

public class RecipesFragment extends Fragment {

    private static final String TAG = RecipesFragment.class.getSimpleName();

    private static final String RECIPES_KEY = "RECIPES";

    public interface RecipeFragmentCallbacks {
        void onRecipeItemSelected(int position);
        void onAddRecipeFromImageSelected();
        void onAddRecipeFromTextSelected();
    }

    private Recipes mRecipes;
    private RecipeFragmentCallbacks mCallback;
    private StaggeredGridView mGridView;
    private View mTranslucentPane;

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

        mTranslucentPane  = getActivity().findViewById(R.id.translucent_pane);
        mGridView = (StaggeredGridView) getActivity().findViewById(R.id.recipes_grid);

        FloatingActionsMenu addRecipeMenu = (FloatingActionsMenu) getActivity().findViewById(R.id.add_recipe_menu);
        FloatingActionButton addFromImageBtn = (FloatingActionButton) getActivity().findViewById(R.id.add_recipe_from_image_button);
        FloatingActionButton addFromTextBtn = (FloatingActionButton) getActivity().findViewById(R.id.add_recipe_from_text_button);

        // setting size in xml causes exception like https://code.google.com/p/android/issues/detail?id=60055
        addFromImageBtn.setSize(FloatingActionButton.SIZE_MINI);
        addFromTextBtn.setSize(FloatingActionButton.SIZE_MINI);

        addRecipeMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                mTranslucentPane.setBackgroundColor(getResources().getColor(R.color.half_translucent));
                mGridView.setEnabled(false);
            }

            @Override
            public void onMenuCollapsed() {
                mTranslucentPane.setBackgroundColor(getResources().getColor(R.color.translucent));
                mGridView.setEnabled(true);
            }
        });

        addFromImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onAddRecipeFromImageSelected();
            }
        });

        addFromTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onAddRecipeFromTextSelected();
            }
        });

        RecipeViewAdapter mAdapter = new RecipeViewAdapter(getActivity(), mRecipes.getRecipes());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
