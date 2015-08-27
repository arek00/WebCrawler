package com.arek00.webCrawler.Downloaders;

import java.io.IOException;

public interface IDownloader {
    String downloadURL(String url) throws IOException;
}
