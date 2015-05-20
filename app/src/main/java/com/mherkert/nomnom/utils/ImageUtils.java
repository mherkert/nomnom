package com.mherkert.nomnom.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;

// TODO black/white filter?
public class ImageUtils {
    public static Uri grayScaleFilter(Context context,
                                      Uri pathToImageFile) {
        Bitmap grayScaleImage = null;

        try (InputStream inputStream = new FileInputStream(pathToImageFile.toString())) {
            Bitmap originalImage =
                    BitmapFactory.decodeStream(inputStream);

            // Bail out of we get an invalid bitmap.
            if (originalImage == null)
                return null;

            grayScaleImage =
                    originalImage.copy(originalImage.getConfig(),
                            true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        boolean hasTransparent = grayScaleImage.hasAlpha();
        int width = grayScaleImage.getWidth();
        int height = grayScaleImage.getHeight();

        // A common pixel-by-pixel grayscale conversion algorithm
        // using values obtained from en.wikipedia.org/wiki/Grayscale.
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {

                // Check if the pixel is transparent in the original
                // by checking if the alpha is 0
                if (hasTransparent
                        && ((grayScaleImage.getPixel(j, i) & 0xff000000) >> 24) == 0) {
                    continue;
                }

                // Convert the pixel to grayscale.
                int pixel = grayScaleImage.getPixel(j, i);
                int grayScale =
                        (int) (Color.red(pixel) * .299
                                + Color.green(pixel) * .587
                                + Color.blue(pixel) * .114);
                grayScaleImage.setPixel(j, i,
                        Color.rgb(grayScale, grayScale, grayScale)
                );
            }
        }

        return createDirectoryAndSaveFile
                (context,
                        grayScaleImage,
                        // Name of the image file that we're filtering.
                        pathToImageFile.toString());
    }

    private static Uri createDirectoryAndSaveFile(Context context,
                                                  Bitmap image,
                                                  String fileName) {
        File directory =
                new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DCIM)
                        + "/ImageDir");

        if (!directory.exists()) {
            File newDirectory =
                    new File(directory.getAbsolutePath());
            newDirectory.mkdirs();
        }

        File file = new File(directory,
                getTemporaryFilename(fileName));
        if (file.exists())
            file.delete();

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            image.compress(Bitmap.CompressFormat.JPEG,
                    100,
                    outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            // Indicate a failure.
            return null;
        }

        // Get the absolute path of the image.
        String absolutePathToImage = file.getAbsolutePath();

        // Provide metadata so the downloaded image is viewable in the
        // Gallery.
        ContentValues values =
                new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,
                fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,
                fileName);
        values.put(MediaStore.Images.Media.DATE_TAKEN,
                System.currentTimeMillis ());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                file.getName().toLowerCase(Locale.getDefault()));
        values.put("_data",
                absolutePathToImage);

        ContentResolver cr =
                context.getContentResolver();

        // Store the metadata for the image into the Gallery.
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);

//        Log.d(TAG,
//                "absolute path to image file is "
//                        + absolutePathToImage);

        return Uri.parse(absolutePathToImage);
    }

    static private String getTemporaryFilename(final String url) {
        // This is what you'd normally call to get a unique temporary
        // filename, but for testing purposes we always name the file
        // the same to avoid filling up student phones with numerous
        // files!
        //
        // return Base64.encodeToString(url.getBytes(),
        //                              Base64.NO_WRAP)
        //                              + System.currentTimeMillis());
        return Base64.encodeToString(url.getBytes(),
                Base64.NO_WRAP);
    }
}
