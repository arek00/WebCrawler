package com.arek00.webCrawler.Downloaders;

import org.junit.BeforeClass;
import org.junit.Test;


public class SimpleDownloaderTest {

    private static IDownloader downloader;

    @BeforeClass
    public static void init() {
        downloader = new SimpleDownloader();
    }

    @Test
    public void shouldDownloadPage() {

    }

}