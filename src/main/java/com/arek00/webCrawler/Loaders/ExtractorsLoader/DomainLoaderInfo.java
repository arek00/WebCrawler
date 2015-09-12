package com.arek00.webCrawler.Loaders.ExtractorsLoader;


import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Attribute;

public class DomainLoaderInfo {
    @Attribute private String domainFilePath;
    @Attribute private String domainName;

    public DomainLoaderInfo(String resourcePath, String domainName) {
        ObjectValidator.nullPointerValidate(resourcePath, domainName);

        this.domainName = domainName;
        this.domainFilePath = resourcePath;
    }


    public DomainLoaderInfo() {
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainFilePath() {
        return domainFilePath;
    }

    public void setDomainFilePath(String domainFilePath) {
        this.domainFilePath = domainFilePath;
    }

    public String getDomainName() {
        return domainName;
    }

}
