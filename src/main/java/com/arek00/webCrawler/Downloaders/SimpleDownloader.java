package com.arek00.webCrawler.Downloaders;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Simple downloader that returns raw html code from given page.
 */
public class SimpleDownloader implements IDownloader {

    public SimpleDownloader() {

    }

    /**
     * Return HTML code from given url
     *
     * @param url
     * @return
     */
    public String downloadURL(String url) throws IOException {
        String htmlCode = Jsoup.connect(url).get().html();
        return htmlCode;
    }
}
