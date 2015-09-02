package com.arek00.webCrawler.Extractors.LinkExtractors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainValidator {

    private static final String URL_DOMAIN_PATTERN = "^.*://[^/]*(/|$)";

    public static boolean isInsideDomain(String url, String domain) {
        String clearedUrl = removePath(url);

        return clearedUrl.contains(domain);
    }

    /**
     * Removes unnecessary part of link that would contains redirects.
     *
     * @param url
     * @return
     */
    private static String removePath(String url) {

        Pattern pattern = Pattern.compile(URL_DOMAIN_PATTERN);
        Matcher matcher = pattern.matcher(url);
        String splitUrl = "";

        if (matcher.find()) {
            splitUrl = url.substring(0, matcher.end());
        }

        return splitUrl;
    }
}
