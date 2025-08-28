package com.lark.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FileUtil {
    private static final int BUFFER_SIZE = 2048;

    public FileUtil() {
    }

    public static boolean deleteFile(String filename) {
        return deleteFile(new File(filename));
    }

    public static boolean deleteFile(File file) {
        return file.exists() && file.isFile() ? file.delete() : true;
    }

    public static boolean rmDir(String dir) {
        return rmDir(new File(dir));
    }

    public static boolean rmDir(File dir) {
        boolean result = true;
        if (dir.exists() && dir.isDirectory()) {
            File[] list = dir.listFiles();
            if (list != null && list.length > 0) {
                for(File item : list) {
                    if (item.isFile()) {
                        result &= deleteFile(item);
                    } else {
                        result &= rmDir(item);
                    }
                }
            }

            result &= dir.delete();
        }

        return result;
    }

    public static boolean checkIfFileExists(String filepath) {
        File file = new File(filepath);
        return file.isFile() && file.exists();
    }

    public static boolean mkdirs(String directory) {
        File dir = new File(directory);
        return dir.mkdirs();
    }

    public static boolean mkdirs(File dir) {
        return dir.mkdirs();
    }

    public static boolean mkdirsIfNotExists(String directory) {
        File dir = new File(directory);
        return !dir.exists() ? mkdirs(dir) : true;
    }

    public static boolean renameTo(String sourceFile, String targetFile) {
        return renameTo(new File(sourceFile), new File(targetFile));
    }

    public static boolean renameTo(File sourceFile, File targetFile) {
        return sourceFile.exists() && sourceFile.isFile() && !targetFile.exists() ? sourceFile.renameTo(targetFile) : false;
    }

    public static String getFileContent(String filepath) throws IOException {
        File file = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder result = new StringBuilder();

        while((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        return result.toString();
    }

    public static long checksum(byte[] data) {
        Checksum checksumEngine = new Adler32();
        checksumEngine.update(data, 0, data.length);
        return checksumEngine.getValue();
    }
}
