package com.arek00.webCrawler.Extractors.LinkExtractors;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SimpleLinkExtractor implements LinkExtractor {

    public SimpleLinkExtractor() {
    }

    /**
     * Return all links from given html code
     *
     * @param htmlCode
     * @return
     */
    public List<String> extractLinks(String htmlCode) {
        return extractLinks(htmlCode, "");
    }


    /**
     * Return all links from given page under given domain.
     *
     * @param htmlCode
     * @param domain
     * @return - List of extracted links saved as Strings
     */
    public List<String> extractLinks(String htmlCode, String domain) {
        List<String> extractedLinks = new ArrayList<String>();

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
