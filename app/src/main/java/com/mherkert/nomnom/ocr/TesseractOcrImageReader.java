package com.mherkert.nomnom.ocr;

import android.net.Uri;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.mherkert.nomnom.utils.FileUtils;

import java.io.File;

public class TesseractOcrImageReader {

    public String imageToText(Uri imagePath) {
        TessBaseAPI baseApi = new TessBaseAPI();
        // TODO: exception handling/support other languages/support download of language data
        baseApi.init(FileUtils.getStorageDirectory().getAbsolutePath(), "eng");
        baseApi.ReadConfigFile(".config");
        baseApi.setImage(new File(imagePath.getPath()));
        String recognizedText = baseApi.getUTF8Text();
        baseApi.end();
        return recognizedText;
    }
}
