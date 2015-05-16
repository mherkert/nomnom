package com.mherkert.nomnom.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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

    public RecipeViewAdapter(Context context, List<Recipe> recipes) {
        super();
        this.mContext = context;
        this.mRecipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_recipe_html, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.recipeTextViewSummary);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Recipe recipe = mRecipes.get(position);

        if (recipe != null) {
            viewHolder.textViewItem.setText(Html.fromHtml(mRecipes.get(position).toHtmlSummary()));
            viewHolder.textViewItem.setBackgroundColor(Color.parseColor(recipe.getMeta().getColor()));
        }
//        if (recipe != null) {
//            viewHolder.textViewItem.setText(Html.fromHtml(mRecipes.get(position).toHtmlSummary()));
//            GradientDrawable background = (GradientDrawable)viewHolder.textViewItem.getBackground().mutate();
//            background.setColor(Color.parseColor(recipe.getMeta().getColor()));
//            background.invalidateSelf();
//        }


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
    }
}
