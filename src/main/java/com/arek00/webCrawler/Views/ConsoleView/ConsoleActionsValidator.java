package com.arek00.webCrawler.Views.ConsoleView;


import java.io.File;

public class ConsoleActionsValidator {
    public static void validateDirectoryPath(String path) {
        File file = new File(path);

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Path " + path + " is a file.");
        }

        if (!file.canWrite()) {
            throw new IllegalArgumentException("Path is invalid or you don't have permissions to save in this path");
        }
    }

    public static void validateFilePath(String path) {
        File file = new File(path);

        if (file.isDirectory()) {
            throw new IllegalArgumentException("Path " + path + " is a directory.");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("File doesn't exist.");
        }
    }
}
