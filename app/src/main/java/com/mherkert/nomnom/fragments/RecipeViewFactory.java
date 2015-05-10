package com.mherkert.nomnom.fragments;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeViewFactory {

    public static TextView getTitleView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextSize(20);
        textView.setPadding(16, 8, 16, 8);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static TextView getIngredientView(Context context, String text){
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextSize(10);
        textView.setPadding(24, 4, 4, 4);// in pixels (left, top, right, bottom)
        return textView;
    }

    public static TextView getDescriptionView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextSize(10);
        textView.setPadding(16, 4, 4, 4);// in pixels (left, top, right, bottom)
        return textView;
    }

}
