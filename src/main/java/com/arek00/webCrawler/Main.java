package com.arek00.webCrawler;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Entities.Domains.IDomain;
import com.arek00.webCrawler.Extractors.ArticleExtractors.ArticleExtractor;
import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;
import com.arek00.webCrawler.Extractors.ContentExtractors.ElementAttributes;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;
import com.arek00.webCrawler.Extractors.ContentExtractors.SimpleContentExtractor;
import com.arek00.webCrawler.Extractors.ExtractorsLoader.DomainLoaderInfo;
import com.arek00.webCrawler.Extractors.ExtractorsLoader.DomainsList;
import com.arek00.webCrawler.Extractors.LinkExtractors.LinkExtractor;
import com.arek00.webCrawler.Extractors.LinkExtractors.SimpleLinkExtractor;
import com.arek00.webCrawler.Queues.IQueue;
import com.arek00.webCrawler.Queues.SimpleLinksQueue;
import com.arek00.webCrawler.Queues.VisitedLinkRegister;
import com.arek00.webCrawler.Serializers.ISerializer;
import com.arek00.webCrawler.Serializers.XMLSerializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String PATH = "C:\\articles\\interia.xml";
    private static final String EXTRACTORS_PATH = "C:\\articles\\domains.xml";
    private static final String DOMAIN = "interia.pl";
    private static final String STARTING_URL = "http://www.interia.pl";



    public static void main(String[] args) {
        try {
            serializeExtractorsList(EXTRACTORS_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem with serialization file. " + e.getMessage());
        }
    }

    private static void serializeDomain(String path) throws Exception {

        ElementAttributes titleAttributes = new ElementAttributes("title", "", "");
        ElementAttributes contentAttributes = new ElementAttributes("div", "itemprop", "articleBody");

        IContentExtractor contentExtractor = new SimpleContentExtractor(contentAttributes);
        IContentExtractor titleExtractor = new SimpleContentExtractor(titleAttributes);
        IArticleExtractor extractor = new ArticleExtractor(contentExtractor, titleExtractor);
        IDomain domain = new Domain(DOMAIN, STARTING_URL, extractor);

        File serializationFile = new File(path);

        ISerializer serializer = new XMLSerializer();
        serializer.serialize(domain, serializationFile);
    }

    private static void serializeExtractorsList(String path) throws Exception {
        List<DomainLoaderInfo> domainsInfo = new ArrayList<DomainLoaderInfo>();
        domainsInfo.add(new DomainLoaderInfo("/domains/extractors/interia.xml", "interia.pl"));

        DomainsList list = new DomainsList();
        list.setDomainsList(domainsInfo);

        File serializationFile = new File(path);

        ISerializer serializer = new XMLSerializer();
        serializer.serialize(list, serializationFile);
    }
}
