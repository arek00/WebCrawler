package com.arek00.webCrawler.Entities.Domains;

import com.arek00.webCrawler.Entities.Articles.IArticle;
import com.arek00.webCrawler.Extractors.ArticleExtractors.IArticleExtractor;
import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Holding information about domain: domain name, main domain page,
 * extractor set to extract articles in given domain.
 */

@Root
public class Domain implements IDomain {

    @Attribute private String domainName;
    @Attribute private String mainPage;
    @Element private IArticleExtractor extractor;

    public Domain(String domainName, String mainPageAddress, IArticleExtractor extractor) {
        ObjectValidator.nullPointerValidate(domainName, mainPageAddress, extractor);

        this.domainName = domainName;
        this.mainPage = mainPageAddress;
        this.extractor = extractor;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public String getDomainMainPage() {
        return this.mainPage;
    }

    public IArticleExtractor getArticleExtractor() {
        return this.extractor;
    }

    /**
     * Only to serialization purposes
     */

    public Domain() {
    }

    public void setDomainName(String domainName) {
        ObjectValidator.nullPointerValidate(domainName);

        this.domainName = domainName;
    }

    public void setMainPage(String mainPage) {
        ObjectValidator.nullPointerValidate(mainPage);

        this.mainPage = mainPage;
    }

    public void setExtractor(IArticleExtractor extractor) {
        ObjectValidator.nullPointerValidate(extractor);

        this.extractor = extractor;
    }

    @Override
    public String toString() {
        return domainName;
    }

}
