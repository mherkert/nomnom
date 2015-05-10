package com.mherkert.nomnom.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mherkert.nomnom.R;
import com.mherkert.nomnom.domain.Recipe;

import java.util.List;

public class RecipeViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recipe> mRecipes;
    private int[] mColors;

    public RecipeViewAdapter(Context context, List<Recipe> recipes) {
        super();
        this.mContext = context;
        this.mRecipes = recipes;
        mColors = context.getResources().getIntArray(R.array.rainbow);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
//            convertView = inflater.inflate(R.layout.item_recipe_dynamic, parent, false);
            convertView = inflater.inflate(R.layout.item_recipe_html, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.recipeTextViewSummary);
//            viewHolder = new ViewHolderItem();
//            viewHolder.recipeContainer = (LinearLayout) convertView.findViewById(R.id.recipe_container);


            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Recipe recipe = mRecipes.get(position);


        // assign values if the recipe object is not null
        if (recipe != null) {
            // TODO adding views dynamically probably does not perform well when scrolling, consider this usage for full view only
//            viewHolder.recipeContainer.setBackgroundColor(Color.parseColor(recipe.getMeta().getColor()));
//            viewHolder.recipeContainer.addView(RecipeViewFactory.getTitleView(mContext, recipe.getTitle()));
//            if (recipe.getDescription() != null && !recipe.getDescription().isEmpty())
//                viewHolder.recipeContainer.addView(RecipeViewFactory.getDescriptionView(mContext, recipe.getDescription()));
//            int count = 0;
//            for (String ingredient : recipe.getIngredients()) {
//                if (count < 3) {
//                    count++;
//                    viewHolder.recipeContainer.addView(RecipeViewFactory.getIngredientView(mContext, ingredient));
//                } else break;
//            }
//            viewHolder.recipeContainer.addView(RecipeViewFactory.getIngredientView(mContext, "..."));

            viewHolder.textViewItem.setText(Html.fromHtml(mRecipes.get(position).toHtmlSummary()));
            viewHolder.textViewItem.setBackgroundColor(Color.parseColor(recipe.getMeta().getColor()));
//            viewHolder.textViewItem.setBackgroundColor(mContext.getResources().getColor(mColors[randInt(0, mColors.length - 1)]));
//            viewHolder.textViewItem.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolderItem {
        TextView textViewItem;
        LinearLayout recipeContainer;
    }

//    public static int randInt(int min, int max) {
//        Random rand = new Random();
//        return rand.nextInt((max - min) + 1) + min;
//    }
}
