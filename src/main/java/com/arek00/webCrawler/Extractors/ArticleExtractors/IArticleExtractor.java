package com.arek00.webCrawler.Extractors.ArticleExtractors;

import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;

import java.io.IOException;

public interface IArticleExtractor {
    public boolean isArticle(String url) throws IOException;

    public IArticle getArticle(String url) throws IOException;

    public void setTitleExtractor(IContentExtractor titleExtractor);

    public void setContentExtractor(IContentExtractor contentExtractor);
}
