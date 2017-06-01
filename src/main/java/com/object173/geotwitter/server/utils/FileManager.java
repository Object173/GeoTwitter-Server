package com.object173.geotwitter.server.utils;

import com.object173.geotwitter.server.contract.ResourcesContract;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class FileManager {

    public static String writeFile(final byte[] byteArray, final String path, final String filename) {
        if(byteArray == null || path == null || filename == null) {
            return null;
        }

        try {
            final String absPath = ResourcesContract.getAbsolutePath(path);
            final File directory = new File(absPath);
            if(!directory.exists()) {
                directory.mkdirs();
            }
            final File newFile = new File(absPath, filename);
            FileUtils.writeByteArrayToFile(newFile, byteArray);

            return path + filename;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static byte[] readFile(final String path) {
        if(path == null) {
            return null;
        }
        final File file = new File(ResourcesContract.getAbsolutePath(path));
        if(!file.exists()) {
            return null;
        }
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (final Exception e) {
            return null;
        }
    }

    public static boolean deleteFile(final String path) {
        if(path == null) {
            return false;
        }
        final File file = new File(ResourcesContract.getAbsolutePath(path));
        return file.delete();
    }

    public static File getFile(final String path) {
        if(path == null) {
            return null;
        }
        return new File(ResourcesContract.getAbsolutePath(path));
    }
}
