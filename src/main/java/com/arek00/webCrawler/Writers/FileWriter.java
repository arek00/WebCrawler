package com.arek00.webCrawler.Writers;


import java.io.IOException;

public class FileWriter {

    /**
     * Write content to file in given path
     *
     * @param path
     * @param content
     * @throws IOException
     */
    public static void writeToFile(String filePath, String content) throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(filePath);
        writer.write(content);
        writer.close();
    }
}
