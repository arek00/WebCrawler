package com.arek00.webCrawler.Entities;

import com.arek00.webCrawler.Validators.ObjectValidator;

import java.io.IOException;

/**
 * Representation of article.
 */
public class Article implements IArticle {

    private String title;
    private String content;
    private String sourceUrl;

    public Article(String title, String content, String sourceUrl) throws IOException {
        ObjectValidator.nullPointerValidate(title, content, sourceUrl);

        this.title = title;
        this.content = content;
        this.sourceUrl = sourceUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getImageSource() {
        throw new UnsupportedOperationException("Operation not yet implemented");
    }

    public String getDate() {
        throw new UnsupportedOperationException("Operation not yet implemented");
    }

    public String getSource() {
        return this.sourceUrl;
    }
}
