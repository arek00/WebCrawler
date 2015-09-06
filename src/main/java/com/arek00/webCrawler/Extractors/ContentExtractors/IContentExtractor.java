package com.arek00.webCrawler.Extractors.ContentExtractors;


public interface IContentExtractor {

    public String extractContent(String htmlCode);

    public boolean containsContent(String htmlCode);

}
