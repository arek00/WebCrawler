package com.arek00.webCrawler.Model;

import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;
import com.arek00.webCrawler.Helpers.MD5Generator;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class Model {

    private final String DOMAINS_RESOURCE = "/domains/domains.xml";
    private boolean hardStop = false;


    private DownloadingStatistic statistic;
    private String articlesDirectory;
    private IQueue queue;
    private VisitedLinkRegister register;

    private List<Domain> domainsList;
    private Domain domain;
    private LinkExtractor linkExtractor;

    private ISerializer serializer;
    private int articlesNumber;
    private String message;


    private List<IListener> onStatisticUpdateListeners = new ArrayList<IListener>();
    private List<IListener> onStartDownloadingListeners = new ArrayList<IListener>();
    private List<IListener> onStopDownloadingListeners = new ArrayList<IListener>();
    private List<IListener> messageListeners = new ArrayList<IListener>();

    private Thread downloadingThread;

    public Model() {
        serializer = new XMLSerializer();
        linkExtractor = new SimpleLinkExtractor();
        statistic = new DownloadingStatistic();

        try {
            this.domainsList = loadDomains(loadDomainsList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DomainsList loadDomainsList() throws Exception {
        InputStream domainsSerializationFile = getClass().getResourceAsStream(DOMAINS_RESOURCE);
        return this.serializer.deserialize(DomainsList.class, domainsSerializationFile);
    }

    private List<Domain> loadDomains(DomainsList domainsList) throws Exception {
        List<DomainLoaderInfo> domains = domainsList.getDomainsList();
        List<Domain> deserializedDomains = new ArrayList<Domain>();

        for (DomainLoaderInfo domainInfo : domains) {

            InputStream file = getClass().getResourceAsStream(domainInfo.getDomainFilePath());

            Domain domain = serializer.deserialize(Domain.class, file);
            deserializedDomains.add(domain);
        }

        return deserializedDomains;
    }


    public void setDomain(Domain domain) {

        try {
            ObjectValidator.nullPointerValidate(domain);
        } catch (Exception e) {
            throw new NullPointerException("Please set correct domain.");
        }

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

    public void loadQueueFile(String serializedFilePath) throws Exception {
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

    public void loadVisitedLinks(String visitedLinksPath) throws Exception {
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

    public void startDownloading() throws Exception {
        hardStop = false;
        statistic.setDownloadedFiles(0);
        statistic.setVisitedLinks(0);
        statistic.setQueueLength(queue.size());

        downloadingThread = new Thread(new DownloadingThread());
        downloadingThread.start();

    }

    public void pauseDownloading() {

    }

    public void stopDownloading() {
        hardStop = true;
    }

    private boolean downloadingCondition(int downloadedArticles, boolean elementsInQueue) {
        return (articlesNumber < 0 || downloadedArticles < articlesNumber) && elementsInQueue && !hardStop;
    }

    private void downloadIfArticle(String pageUrl) throws Exception {
        IArticleExtractor articlesExtractor = this.domain.getArticleExtractor();

        if (articlesExtractor.isArticle(pageUrl)) {
            message("Downloading: " + pageUrl);
            IArticle article = articlesExtractor.getArticle(pageUrl);
            saveToFile(article);
        }
    }

    private void saveToFile(IArticle article) throws Exception {
        String hashFileName = createArticleFileName(article);

        String separator = File.separator;
        String path = articlesDirectory + separator + hashFileName + ".xml";
        File fileToSave = new File(path);
        serializer.serialize(article, fileToSave);
        statistic.setDownloadedFiles(statistic.getDownloadedFiles() + 1);
        message("Saved " + path + " file.");
    }

    private String createArticleFileName(IArticle article) {
        return MD5Generator.generateMD5Hash(article.getSource());
    }

    private List<String> extractLinks(String pageUrl) throws IOException {
        return linkExtractor.extractLinks(pageUrl, domain.toString());
    }

    public DownloadingStatistic getDownloadingStatistics() {
        return this.statistic;
    }

    public void setStatisticUpdateListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        onStatisticUpdateListeners.add(listener);
    }

    public void setOnStartDownloadListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        this.onStartDownloadingListeners.add(listener);
    }

    public void setOnStopDownloadListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        this.onStopDownloadingListeners.add(listener);
    }

    private void updateDownloadingStatistics() {
        for (IListener listener : onStatisticUpdateListeners) {
            listener.inform();
        }
    }

    private void informOnStartDownloadingListeners() {
        for (IListener onStartListener : onStartDownloadingListeners) {
            onStartListener.inform();
        }
    }

    private void informOnStopDownloadingListeners() {
        for (IListener onStopListener : onStopDownloadingListeners) {
            onStopListener.inform();
        }
    }

    public void setMessageListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);
        messageListeners.add(listener);
    }

    public String getMessage() {
        return this.message;
    }

    public void message(String message) {
        this.message = message;

        for (IListener listener : messageListeners) {
            listener.inform();
        }
    }

    class DownloadingThread implements Runnable {

        public void run() {
            informOnStartDownloadingListeners();

            message("Start downloading: " + domain.toString());
            try {
                queue.add(linkExtractor.extractLinks(domain.getDomainMainPage(), domain.getDomainName()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (downloadingCondition(statistic.getDownloadedFiles(), queue.hasNext())) {

                String currentDownloadedPageUrl = queue.next();
                statistic.setVisitedLinks(statistic.getVisitedLinks() + 1);
                String message = "Visited link: " + currentDownloadedPageUrl;

                message(message);

                try {
                    queue.add(extractLinks(currentDownloadedPageUrl));
                    downloadIfArticle(currentDownloadedPageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                statistic.setQueueLength(queue.size());


                updateDownloadingStatistics();
            }

            informOnStopDownloadingListeners();
        }
    }

    public void serializeQueue(String path) throws Exception {
        serializeFile(this.queue, path);
    }

    public void serializeVisitedLinks(String path) throws Exception {
        serializeFile(register, path);
    }

    private void serializeFile(Object object, String path) throws Exception {
        serializer.serialize(object, new File(path));
    }
}
