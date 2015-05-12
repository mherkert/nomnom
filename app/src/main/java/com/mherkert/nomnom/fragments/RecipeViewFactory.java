package com.mherkert.nomnom.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
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
//        textView.setTextSize(20);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Title);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPadding(16, 8, 16, 8);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static TextView getDescriptionView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
//        textView.setTextSize(14);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Small);
        textView.setTypeface(null, Typeface.ITALIC);
        textView.setPadding(16, 4, 4, 4);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static TextView getIngredientView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Subhead);
//        textView.setTextSize(14);
        textView.setPadding(24, 4, 4, 4);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static TextView getInstructionView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextAppearance(context, R.style.Base_TextAppearance_AppCompat_Body1);
//        textView.setTextSize(14);
        textView.setPadding(16, 4, 4, 4);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static View getDividerView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.divider, null);
    }
}
