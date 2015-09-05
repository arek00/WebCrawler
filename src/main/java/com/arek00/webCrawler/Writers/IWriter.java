package com.arek00.webCrawler.Writers;


import java.io.IOException;

public interface IWriter {

    public void write(String path) throws IOException;

    public void write(String path, String content) throws IOException;

}
