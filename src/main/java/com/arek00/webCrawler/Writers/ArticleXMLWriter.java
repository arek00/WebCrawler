package com.arek00.webCrawler.Writers;

import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Validators.ObjectValidator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;

/**
 */
public class ArticleXMLWriter implements IWriter {

    private IArticle article;
    private JAXBContext context;
    private Marshaller marshaller;

    public ArticleXMLWriter(JAXBContext context) throws JAXBException {
        ObjectValidator.nullPointerValidate(context);

        this.context = context;
        this.marshaller = this.context.createMarshaller();
    }

    public void setArticle(IArticle article) {
        ObjectValidator.nullPointerValidate(article);

        this.article = article;
    }

    public void write(String path) throws IOException, JAXBException {
        ObjectValidator.nullPointerValidate(path, article);

        File marshallerFile = new File(path);
        marshaller.marshal(this.article, marshallerFile);

    }

    public void write(String path, String content) throws IOException {
        throw new UnsupportedOperationException("ArticleXMLWriter supports only method without content as parameter.");
    }
}
