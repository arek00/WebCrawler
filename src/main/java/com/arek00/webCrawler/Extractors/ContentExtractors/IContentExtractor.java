package com.arek00.webCrawler.Extractors.ContentExtractors;


public interface IContentExtractor {
    public void setPage(String htmlCode);

    public String extractContent();

    public boolean containsContent();

}
