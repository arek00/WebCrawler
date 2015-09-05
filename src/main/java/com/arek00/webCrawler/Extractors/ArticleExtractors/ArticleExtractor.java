package com.arek00.webCrawler.Extractors.ArticleExtractors;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.Article;
import com.arek00.webCrawler.Entities.IArticle;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;
import com.arek00.webCrawler.Validators.ObjectValidator;

import java.io.IOException;

public class ArticleExtractor {

    private IDownloader downloader;
    private IContentExtractor contentExtractor;
    private IContentExtractor titleExtractor;

    public ArticleExtractor(IContentExtractor contentExtractor, IContentExtractor titleExtractor) {
        ObjectValidator.nullPointerValidate(contentExtractor, titleExtractor);

        downloader = new SimpleDownloader();
    }


    public boolean isArticle(String url) throws IOException {
        ObjectValidator.nullPointerValidate(url);

        String htmlCode = downloader.downloadURL(url);
        contentExtractor.setPage(htmlCode);

        return contentExtractor.containsContent();
    }

    public IArticle getArticle(String url) throws IOException {
        ObjectValidator.nullPointerValidate(url);

        String htmlCode = downloader.downloadURL(url);
        String title = titleExtractor.extractContent();
        String content = contentExtractor.extractContent();


        return new Article(title, content, url);
    }

}
