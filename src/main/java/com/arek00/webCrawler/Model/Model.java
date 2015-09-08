package com.arek00.webCrawler.Model;

import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.IArticle;
import com.arek00.webCrawler.Extractors.ArticleExtractors.ArticleExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.LinkExtractor;
import com.arek00.webCrawler.Queues.IQueue;
import com.arek00.webCrawler.Queues.VisitedLinkRegister;
import com.arek00.webCrawler.Serializers.ISerializer;
import com.arek00.webCrawler.Serializers.XMLSerializer;
import com.arek00.webCrawler.Validators.ObjectValidator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 */
public class Model {
    private int downloadedArticles = 0;
    private int visitedLinks = 0;
    private int linksInQueue = 0;

    private IDownloader downloader;

    private String articlesDirectory;
    private String domain;
    private IQueue queue;
    private VisitedLinkRegister register;


    private ArticleExtractor articlesExtractor;
    private LinkExtractor linkExtractor;

    private ISerializer serializer;
    private int articlesNumber;


    private String queueFilePath;
    private String articlesExtractorPath;

    public Model() {
        downloader = new SimpleDownloader();
        serializer = new XMLSerializer();
    }


    public void setDomain(String domain) {
        ObjectValidator.nullPointerValidate(domain);

        this.domain = domain;
    }

    public void restoreQueue(String serializedFilePath) throws Exception {
        ObjectValidator.nullPointerValidate(serializedFilePath);

        File file = new File(serializedFilePath);
        queue = serializer.deserialize(IQueue.class, file);
    }

    public void restoreVisitedLinks(String visitedLinksPath) throws Exception {
        ObjectValidator.nullPointerValidate(visitedLinksPath);

        this.register = serializer.deserialize(register.getClass(), new File(visitedLinksPath));
        queue.setRegister(register);
    }


    public void createArticlesExtractor(String serializationFilePath) throws Exception {
        ObjectValidator.nullPointerValidate(serializationFilePath);

        File file = new File(serializationFilePath);
        this.articlesExtractor = serializer.deserialize(ArticleExtractor.class, file);
    }

    public void startDownloadingArticles() throws Exception {
        this.downloadedArticles = 0;
        this.visitedLinks = 0;
        this.linksInQueue = queue.size();

        while (downloadedArticles < articlesNumber || queue.hasNext()) {
            String currentDownloadedPageUrl = queue.next();
            List<String> extractedLinks = linkExtractor.extractLinks(currentDownloadedPageUrl, this.domain);

            if (articlesExtractor.isArticle(currentDownloadedPageUrl)) {
                IArticle article = articlesExtractor.getArticle(currentDownloadedPageUrl);

                File fileToSave = new File(articlesDirectory + "\\" + downloadedArticles + ".xml");

                serializer.serialize(article, fileToSave);
                ++downloadedArticles;
            }

        }

    }


}
