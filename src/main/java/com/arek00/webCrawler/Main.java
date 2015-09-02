package com.arek00.webCrawler;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Extractors.ContentExtractors.ElementAttributes;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;
import com.arek00.webCrawler.Extractors.ContentExtractors.SimpleContentExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.LinkExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.SimpleLinkExtractor;
import com.arek00.webCrawler.Queues.IQueue;
import com.arek00.webCrawler.Queues.SimpleLinksQueue;
import com.arek00.webCrawler.Writers.FileWriter;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String PATH = "C:\\articles";
    private static final String DOMAIN = "interia.pl";
    private static final String STARTING_URL = "http://www.interia.pl";


    public static void main(String[] args) {
        IDownloader downloader = new SimpleDownloader();
        IQueue queue = new SimpleLinksQueue();
        queue.add(STARTING_URL);
        LinkExtractor linkExtractor = new SimpleLinkExtractor();
        String htmlCode = null;

        ElementAttributes interiaArticle = new ElementAttributes("div", "itemprop", "articleBody");
        ElementAttributes interiaTitle = new ElementAttributes("title", "", "");

        int maxLinks = 500;

        int fileCounter = 0;
        while (queue.hasNext()) {
            String link = queue.next();
            System.out.println("Visited link " + link);

            try {
                htmlCode = downloader.downloadURL(link);
            } catch (IOException e) {
                e.printStackTrace();
            }

            IContentExtractor articleExtractor = new SimpleContentExtractor(htmlCode, interiaArticle);
            IContentExtractor titleExtractor = new SimpleContentExtractor(htmlCode, interiaTitle);
            List<String> links = linkExtractor.extractLinks(htmlCode, DOMAIN);
            queue.add(links);


            if (articleExtractor.containsContent()) {
                String content = titleExtractor.extractContent() + '\n' + articleExtractor.extractContent();

                String fileName = String.format("%s\\%d.txt", PATH, fileCounter);

                try {
                    FileWriter.writeToFile(fileName, content);
                } catch (IOException e) {
                    e.printStackTrace();

                    System.out.println("Couldn't write file");
                }

                ++fileCounter;

                if (fileCounter > maxLinks) {
                    break;
                }
            }
        }
    }
}
