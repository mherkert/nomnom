package com.mherkert.nomnom.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static String loadData(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
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
}
