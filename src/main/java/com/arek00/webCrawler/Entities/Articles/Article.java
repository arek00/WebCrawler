package com.arek00.webCrawler.Entities.Articles;

import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.IOException;

/**
 * Representation of article.
 */
@Root
public class Article implements IArticle {

    @Element private String title;
    @Element private String content;
    @Element private String sourceUrl;

    public Article(String title, String content, String sourceUrl) throws IOException {
        ObjectValidator.nullPointerValidate(title, content, sourceUrl);

        this.title = title;
        this.content = content;
        this.sourceUrl = sourceUrl;
    }

    /*
     * Serialization mandatory implementation
     */

    public Article() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
