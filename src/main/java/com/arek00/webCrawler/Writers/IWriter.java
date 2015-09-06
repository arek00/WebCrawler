package com.arek00.webCrawler.Writers;


import java.io.IOException;

public interface IWriter {

    public void write(String path) throws Exception;

    public void write(String path, String content) throws Exception;

}
