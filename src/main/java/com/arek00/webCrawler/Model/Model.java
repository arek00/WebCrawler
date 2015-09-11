package com.arek00.webCrawler.Model;

import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Extractors.ArticleExtractors.ArticleExtractor;
import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.LinkExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.SimpleLinkExtractor;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Queues.IQueue;
import com.arek00.webCrawler.Queues.SimpleLinksQueue;
import com.arek00.webCrawler.Queues.VisitedLinkRegister;
import com.arek00.webCrawler.Serializers.ISerializer;
import com.arek00.webCrawler.Serializers.XMLSerializer;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.Validators.StringValidator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


    private IArticleExtractor articlesExtractor;
    private LinkExtractor linkExtractor;

    private ISerializer serializer;
    private int articlesNumber;


    private String queueFilePath;
    private String articlesExtractorPath;

    private List<IListener> onChangeValuesListener = new ArrayList<IListener>();

    public Model() {
        downloader = new SimpleDownloader();
        serializer = new XMLSerializer();
        linkExtractor = new SimpleLinkExtractor();
    }


    public void setDomain(String domain) {
        ObjectValidator.nullPointerValidate(domain);
        StringValidator.isEmptyString(domain, "Domain name cannot be empty");

        this.domain = domain;
    }

    public void setArticlesDownloadPath(String path) {
        ObjectValidator.nullPointerValidate(path);
        StringValidator.isEmptyString(path, "Please set path to save articles");

        this.articlesDirectory = path;
    }

    public void restoreQueue(String serializedFilePath) throws Exception {
        ObjectValidator.nullPointerValidate(serializedFilePath);

        if (serializedFilePath.isEmpty()) {
            queue = new SimpleLinksQueue(new VisitedLinkRegister(this.domain), domain);
        } else {
            File file = new File(serializedFilePath);
            if (!file.exists()) {
                throw new IllegalArgumentException("Queue file path is incorrect.");
            }

            queue = serializer.deserialize(SimpleLinksQueue.class, file);
        }
    }

    public void restoreVisitedLinks(String visitedLinksPath) throws Exception {
        ObjectValidator.nullPointerValidate(visitedLinksPath);

        if (visitedLinksPath.isEmpty()) {
            this.register = new VisitedLinkRegister(this.domain);
        } else {
            this.register = serializer.deserialize(VisitedLinkRegister.class, new File(visitedLinksPath));
        }

        queue.setRegister(register);
    }


    public void createArticlesExtractor(String serializationFilePath) throws Exception {
        ObjectValidator.nullPointerValidate(serializationFilePath);
        StringValidator.isEmptyString(serializationFilePath, "Please select articles extractor file");

        File file = new File(serializationFilePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("Selected file does not exist");
        }

        this.articlesExtractor = serializer.deserialize(ArticleExtractor.class, file);
    }

    public void setArticlesNumber(int articlesNumber) {
        this.articlesNumber = articlesNumber;
    }

    public void startDownloadingArticles() throws Exception {
        this.downloadedArticles = 0;
        this.visitedLinks = 0;
        this.linksInQueue = queue.size();
        Thread thread = new Thread(new DownloadingThread());
        thread.start();

        System.out.println("Finished downloading articles");
    }

    private boolean downloadingCondition(int downloadedArticles, boolean elementsInQueue) {
        return (articlesNumber < 0 || downloadedArticles < articlesNumber) && elementsInQueue;
    }

    private void downloadIfArticle(String pageUrl) throws Exception {
        if (articlesExtractor.isArticle(pageUrl)) {
            System.out.println("Downloading: " + pageUrl);

            IArticle article = articlesExtractor.getArticle(pageUrl);

            File fileToSave = new File(articlesDirectory + "\\" + downloadedArticles + ".xml");

            serializer.serialize(article, fileToSave);
            ++downloadedArticles;
        }
    }

    private List<String> extractLinks(String pageUrl) throws IOException {
        return linkExtractor.extractLinks(pageUrl, this.domain);
    }

    public int getDownloadedArticles() {
        return this.downloadedArticles;
    }

    public int getVisitedLinks() {
        return this.visitedLinks;
    }

    public int getLinksInQueue() {
        return this.linksInQueue;
    }

    public void setStatisticValuesListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        onChangeValuesListener.add(listener);
    }

    private void setStatisticValues() {
        for (IListener listener : onChangeValuesListener) {
            listener.inform();
        }
    }

    class DownloadingThread implements Runnable {

        public void run() {
            System.out.println("Start downloading articles");

            while (downloadingCondition(downloadedArticles, queue.hasNext())) {

                String currentDownloadedPageUrl = queue.next();
                ++visitedLinks;
                System.out.println("Visited link: " + currentDownloadedPageUrl);

                try {
                    queue.add(extractLinks(currentDownloadedPageUrl));
                    downloadIfArticle(currentDownloadedPageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                linksInQueue = queue.size();


                setStatisticValues();
            }
        }
    }
}
