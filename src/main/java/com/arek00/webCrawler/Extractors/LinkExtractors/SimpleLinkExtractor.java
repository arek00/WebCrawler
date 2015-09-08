package com.arek00.webCrawler.Extractors.LinkExtractors;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleLinkExtractor implements LinkExtractor {


    private IDownloader downloader;

    public SimpleLinkExtractor() {
        this.downloader = new SimpleDownloader();
    }

    /**
     * Return all links from given html code
     *
     * @param url
     * @return
     */
    public List<String> extractLinks(String url) throws IOException {
        return extractLinks(url, "");
    }


    /**
     * Return all links from given page under given domain.
     *
     * @param url
     * @param domain
     * @return - List of extracted links saved as Strings
     */
    public List<String> extractLinks(String url, String domain) throws IOException {
        List<String> extractedLinks = new ArrayList<String>();

        String htmlCode = this.downloader.downloadURL(url);
        Document page = Jsoup.parse(htmlCode);
        Elements links = page.select("link");
        links.addAll(page.select("a[href]"));

        for (Element link : links) {
            String extractedLink = link.attr("abs:href");

            if (DomainValidator.isInsideDomain(extractedLink, domain)) {
                extractedLinks.add(link.attr("abs:href"));
            }
        }

        return extractedLinks;

    }


}
