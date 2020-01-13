package com.example.projectcpe;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StoreCSV {
    public static void storeCSV(String filename) {
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/MyCSV/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String path = directory_path + filename;
        OutputStream out = null;
        File imageFile = new File(path);

        try {
            out = new FileOutputStream(imageFile);
            // choose JPEG format
            out.flush();
        } catch (FileNotFoundException e) {
            // manage exception ...
        } catch (IOException e) {
            // manage exception ...
        } finally {

            try {
                if (out != null) {
                    out.close();
                }

            } catch (Exception exc) {
            }

        }
    }
}
