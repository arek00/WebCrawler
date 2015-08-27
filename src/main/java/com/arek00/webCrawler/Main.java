package com.arek00.webCrawler;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Extractros.LinkExtractor;
import com.arek00.webCrawler.Extractros.SimpleLinkExtractor;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        IDownloader downloader = new SimpleDownloader();
        String htmlCode = null;

        LinkExtractor extractor = new SimpleLinkExtractor();

        try {
            htmlCode = downloader.downloadURL("http://www.wykop.pl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String domain = "wykop.pl";
        List<String> links = extractor.extractLinks(htmlCode, domain);
        String extractedLinksNumberInfo = String.format("Extracted %d links from %s", links.size(), domain);
        System.out.println(extractedLinksNumberInfo);

        for (String link : links) {
            System.out.println(link);
        }


    }
}
