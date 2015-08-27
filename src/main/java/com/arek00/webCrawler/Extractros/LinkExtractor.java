package com.arek00.webCrawler.Extractros;

import java.util.List;

/**
 * Created by Admin on 2015-08-27.
 */
public interface LinkExtractor {
    List<String> extractLinks(String htmlCode);

    List<String> extractLinks(String htmlCode, String domain);
}
