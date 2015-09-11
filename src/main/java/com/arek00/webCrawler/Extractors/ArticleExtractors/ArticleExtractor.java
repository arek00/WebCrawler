package com.arek00.webCrawler.Extractors.ArticleExtractors;


import com.arek00.webCrawler.Downloaders.IDownloader;
import com.arek00.webCrawler.Downloaders.SimpleDownloader;
import com.arek00.webCrawler.Entities.Articles.Article;
import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Extractors.ContentExtractors.IContentExtractor;
import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.IOException;

public class ArticleExtractor implements IArticleExtractor {

    private IDownloader downloader;
    @Element private IContentExtractor contentExtractor;
    @Element private IContentExtractor titleExtractor;

    public ArticleExtractor(IContentExtractor contentExtractor, IContentExtractor titleExtractor) {
        ObjectValidator.nullPointerValidate(contentExtractor, titleExtractor);

        downloader = new SimpleDownloader();
        this.contentExtractor = contentExtractor;
        this.titleExtractor = titleExtractor;
    }

    public boolean isArticle(String url) throws IOException {
        ObjectValidator.nullPointerValidate(url);

        String htmlCode = downloader.downloadURL(url);
        return contentExtractor.containsContent(htmlCode);
    }

    public IArticle getArticle(String url) throws IOException {
        ObjectValidator.nullPointerValidate(url);

        String htmlCode = downloader.downloadURL(url);

        String title = titleExtractor.extractContent(htmlCode);
        String content = contentExtractor.extractContent(htmlCode);

        return new Article(title, content, url);
    }

    /**
     * Constructor to serialization purposes
     */

    public ArticleExtractor() {
        downloader = new SimpleDownloader();
    }


    public IContentExtractor getTitleExtractor() {
        return titleExtractor;
    }

    public void setTitleExtractor(IContentExtractor titleExtractor) {
        this.titleExtractor = titleExtractor;
    }

    public IContentExtractor getContentExtractor() {
        return contentExtractor;
    }

    public void setContentExtractor(IContentExtractor contentExtractor) {
        this.contentExtractor = contentExtractor;
    }

}
