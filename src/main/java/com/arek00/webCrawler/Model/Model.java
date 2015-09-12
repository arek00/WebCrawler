package com.arek00.webCrawler.Model;

import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Extractors.ArticleExtractors.ArticleExtractor;
import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;
import com.arek00.webCrawler.Loaders.ExtractorsLoader.DomainLoaderInfo;
import com.arek00.webCrawler.Loaders.ExtractorsLoader.DomainsList;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Model {

    private String DOMAINS_RESOURCE;

    private int downloadedArticles = 0;
    private int visitedLinks = 0;
    private int linksInQueue = 0;

    private IDownloader downloader;

    private String articlesDirectory;
    private IQueue queue;
    private VisitedLinkRegister register;

    private List<Domain> domainsList;
    private Domain domain;
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

        setDomainsFilePath();
        try {
            this.domainsList = loadDomains(loadDomainsList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load Domains Serialization File: " + DOMAINS_RESOURCE + " " + e.getMessage());
        }
    }

    private void setDomainsFilePath() {
        this.DOMAINS_RESOURCE = getClass().getResource("/domains/domains.xml").getFile();
        System.out.println(DOMAINS_RESOURCE);
    }

    private DomainsList loadDomainsList() throws Exception {
        File domainsSerializationFile = new File(DOMAINS_RESOURCE);
        return this.serializer.deserialize(DomainsList.class, domainsSerializationFile);
    }

    private List<Domain> loadDomains(DomainsList domainsList) throws Exception {
        List<DomainLoaderInfo> domains = domainsList.getDomainsList();
        List<Domain> deserializedDomains = new ArrayList<Domain>();

        for (DomainLoaderInfo domainInfo : domains) {

            String path = getClass().getResource(domainInfo.getDomainFilePath()).getFile();

            File domainFile = new File(path);
            Domain domain = serializer.deserialize(Domain.class, domainFile);
            deserializedDomains.add(domain);
        }

        System.out.println(String.format("Loaded %d domains", deserializedDomains.size()));

        return deserializedDomains;
    }


    public void setDomain(Domain domain) {
        ObjectValidator.nullPointerValidate(domain);

        this.domain = domain;
    }

    public List<Domain> getDomains() {
        return this.domainsList;
    }

    public void setArticlesDownloadPath(String path) {
        ObjectValidator.nullPointerValidate(path);
        StringValidator.isEmptyString(path, "Please set path to save articles");

        this.articlesDirectory = path;
    }

    public void restoreQueue(String serializedFilePath) throws Exception {
        ObjectValidator.nullPointerValidate(serializedFilePath);

        String domainName = this.domain.toString();

        if (serializedFilePath.isEmpty()) {
            queue = new SimpleLinksQueue(new VisitedLinkRegister(domainName), domainName);
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
            this.register = new VisitedLinkRegister(domain.toString());
        } else {
            this.register = serializer.deserialize(VisitedLinkRegister.class, new File(visitedLinksPath));
        }

        queue.setRegister(register);
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
        IArticleExtractor articlesExtractor = this.domain.getArticleExtractor();

        if (articlesExtractor.isArticle(pageUrl)) {
            System.out.println("Downloading: " + pageUrl);

            IArticle article = articlesExtractor.getArticle(pageUrl);

            File fileToSave = new File(articlesDirectory + "\\" + downloadedArticles + ".xml");

            serializer.serialize(article, fileToSave);
            ++downloadedArticles;
        }
    }

    private List<String> extractLinks(String pageUrl) throws IOException {
        return linkExtractor.extractLinks(pageUrl, domain.toString());
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
