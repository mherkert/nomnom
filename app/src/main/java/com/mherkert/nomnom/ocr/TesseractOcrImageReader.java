package com.mherkert.nomnom.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class TesseractOcrImageReader {

    public String imageToText(String imagePath) {
        TessBaseAPI baseApi = new TessBaseAPI();
// DATA_PATH = Path to the storage
// lang = for which the language data exists, usually "eng"
        baseApi.init("/storage/sdcard/Documents/com.mherkert.nomnom/", "eng");
// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
        baseApi.setImage(new File(imagePath));
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        return recognizedText;
    }


    public static void main(String[] args) {
        TesseractOcrImageReader tesseractOcrImageReader = new TesseractOcrImageReader();
        String text = tesseractOcrImageReader.imageToText("/Users/marion.herkert/Dropbox/NomNom/harissa_recipe.jpg");
        System.out.println("text = " + text);

    }

}
