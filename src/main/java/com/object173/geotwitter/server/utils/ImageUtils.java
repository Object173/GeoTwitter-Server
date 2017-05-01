package com.object173.geotwitter.server.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ярослав on 30.04.2017.
 */
public class ImageUtils {

    public static String writeImage(final byte[] byteArray, final String path) {
        if(byteArray == null || path == null) {
            return "null pointer";
        }

        try {
            File newFile = new File(path);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(byteArray);
            stream.close();

            return path;
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
