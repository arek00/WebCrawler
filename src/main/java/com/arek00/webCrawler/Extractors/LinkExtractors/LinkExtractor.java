package com.arek00.webCrawler.Extractors.LinkExtractors;

import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 2015-08-27.
 */
public interface LinkExtractor {
    List<String> extractLinks(String url) throws IOException;

    List<String> extractLinks(String url, String domain) throws IOException;
}
