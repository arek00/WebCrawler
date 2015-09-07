package com.arek00.webCrawler;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.IArticle;
import com.arek00.webCrawler.Extractors.ArticleExtractors.ArticleExtractor;
import com.arek00.webCrawler.Extractors.ContentExtractors.ElementAttributes;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;
import com.arek00.webCrawler.Extractors.ContentExtractors.SimpleContentExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.LinkExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.SimpleLinkExtractor;
import com.arek00.webCrawler.Queues.IQueue;
import com.arek00.webCrawler.Queues.SimpleLinksQueue;
import com.arek00.webCrawler.Queues.VisitedLinkRegister;
import com.arek00.webCrawler.Serializers.ISerializer;
import com.arek00.webCrawler.Serializers.XMLSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String PATH = "C:\\articles";
    private static final String DOMAIN = "interia.pl";
    private static final String STARTING_URL = "http://www.interia.pl";


    public static void main(String[] args) {
        IDownloader downloader = new SimpleDownloader();
        VisitedLinkRegister register = new VisitedLinkRegister(DOMAIN);

        IQueue queue = new SimpleLinksQueue(register, DOMAIN);
        queue.add(STARTING_URL);
        LinkExtractor linkExtractor = new SimpleLinkExtractor();
        String htmlCode = null;
        ISerializer serializer = new XMLSerializer();

        ElementAttributes interiaArticle = new ElementAttributes("div", "itemprop", "articleBody");
        ElementAttributes interiaTitle = new ElementAttributes("title", "", "");
        IContentExtractor articleExtractor = new SimpleContentExtractor(interiaArticle);
        IContentExtractor titleExtractor = new SimpleContentExtractor(interiaTitle);
        ArticleExtractor extractor = new ArticleExtractor(DOMAIN, articleExtractor, titleExtractor);

        int maxLinks = 5;

        int pagesWithContent = 0;
        int visitedPages = 0;


        while (queue.hasNext()) {
            String link = queue.next();
            System.out.println("Visited link " + link);

            try {
                htmlCode = downloader.downloadURL(link);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> links = linkExtractor.extractLinks(htmlCode, DOMAIN);
            queue.add(links);


            try {
                if (extractor.isArticle(link)) {

                    IArticle article = extractor.getArticle(link);

                    String fileName = String.format("%s\\%d.txt", PATH, pagesWithContent);

                    try {
                        serializer.serialize(article, new File(fileName));

                    } catch (IOException e) {
                        e.printStackTrace();

                        System.out.println("Couldn't write file");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ++pagesWithContent;

                    if (pagesWithContent % 10 == 0) {
                        visitedPages = ((SimpleLinksQueue) queue).visitedLinks();

                        String text = String.format("Visited %d pages, %d pages contained content. %d links in queue", visitedPages, pagesWithContent, ((SimpleLinksQueue) queue).size());
                        System.out.println(text);
                    }

                    if (pagesWithContent > maxLinks) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            serializer.serialize(register, new File(PATH + "\\visitedLinks.xml"));
            serializer.serialize(extractor, new File(PATH + "\\extractors.xml"));
            serializer.serialize(queue, new File(PATH + "\\queue.xml"));
        } catch (Exception e) {
            e.printStackTrace();

            System.out.println("Problem with serialize " + e.getMessage());
        }
    }
}
