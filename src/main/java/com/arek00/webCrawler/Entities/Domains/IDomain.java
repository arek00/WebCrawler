package com.arek00.webCrawler.Entities.Domains;


import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;

public interface IDomain {

    public String getDomainName();
    public String getDomainMainPage();
    public IArticleExtractor getArticleExtractor();

}
