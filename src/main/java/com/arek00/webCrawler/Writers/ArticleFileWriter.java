package com.arek00.webCrawler.Writers;


import com.arek00.webCrawler.Entities.IArticle;
import com.arek00.webCrawler.Validators.ObjectValidator;

import java.io.FileWriter;
import java.io.IOException;

public class ArticleFileWriter implements IWriter {

    private IArticle article;

    public void setArticle(IArticle article) {
        ObjectValidator.nullPointerValidate(article);

        this.article = article;
    }

    public void write(String path) throws IOException {
        if (this.article == null) {
            throw new IllegalArgumentException("Article hasn't been set to save");
        }

        String content = String.format("%s\n%s\nSource:%s", article.getTitle(), article.getContent(), article.getSource());
        write(path, content);
    }

    public void write(String path, String content) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(content);
        writer.close();
    }
}
