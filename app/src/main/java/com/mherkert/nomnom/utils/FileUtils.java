package com.mherkert.nomnom.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static String loadData(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        return readFile(inputStream);
    }

    public static String loadDataFromFile() {
        File recipesFile = getRecipesFile();
        if (recipesFile == null)
            return null;
        else
            try {
                return readFile(new FileInputStream(recipesFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
    }

    public static void writeDataToFile(String text) {
        try {
            FileOutputStream out = new FileOutputStream(new File(getRecipesDir(), "recipes.txt"));
            out.write(text.getBytes(Charset.forName("UTF-8")));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();

        try {

            String line = "";
            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    private static File getRecipesFile() {
        File recipeFile = new File(getRecipesDir(), "recipes.txt");

        if (!recipeFile.exists())
            return null;
        else
            return recipeFile;
    }

    private static File getRecipesDir() {
        File storageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "com.mherkert.nomnom");

        if (!storageDir.exists())
            storageDir.mkdirs();

        return storageDir;
    }
}
