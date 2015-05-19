package com.mherkert.nomnom.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mherkert.nomnom.R;
import com.mherkert.nomnom.ocr.TesseractOcrImageReader;

public class RecipeAddFragment extends Fragment {
    private static final String TAG = RecipeAddFragment.class.getSimpleName();

    private EditText mEditText;
    private ProgressBar mProgressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipe_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditText = (EditText) getActivity().findViewById(R.id.recipe_edittext);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.recipe_image_processor_progress_bar);
    }

    public void updateRecipeAddDisplay(Context context, Uri imagePath) {
        Log.d(TAG, "Add recipe from image' " + imagePath + "'");

        // TODO retain data and asynctask
        AsyncTask<Uri, Integer, String> task = new ReadTextFromImageTask().execute(imagePath);

    }

    public void setResult(String text) {
        mEditText.setText(text);
    }

    class ReadTextFromImageTask extends AsyncTask<Uri, Integer, String> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Uri... params) {
            Log.d(TAG, "Start image processing.");
            TesseractOcrImageReader imageReader = new TesseractOcrImageReader();

            return imageReader.imageToText(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(View.GONE);
            setResult(result);
        }
    }
}
