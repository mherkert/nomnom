package com.mherkert.nomnom.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mherkert.nomnom.R;

public class RecipeViewFactory {

    // TODO use styles?
    public static TextView getTitleView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Title);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(16, 8, 24, 8);
        return textView;
    }

    public static TextView getDescriptionView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Small);
        textView.setTypeface(null, Typeface.ITALIC);
        textView.setPadding(16, 4, 24, 4);
        return textView;
    }

    public static TextView getIngredientView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Subhead);
        textView.setPadding(24, 4, 24, 4);
        return textView;
    }

    public static TextView getInstructionView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Body1);
        textView.setPadding(16, 4, 24, 4);
        return textView;
    }

    public static View getDividerView(Context context) {
        ImageView divider = new ImageView(context);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        lp.setMargins(16, 16, 16, 16);
        divider.setLayoutParams(lp);
        divider.setBackgroundColor(Color.DKGRAY);
        return divider;
    }
}
