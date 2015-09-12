package com.arek00.webCrawler.Loaders.ExtractorsLoader;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class DomainsList {

    @ElementList
    private List<DomainLoaderInfo> domainsList;

    public DomainsList() {
        this.domainsList = new ArrayList<DomainLoaderInfo>();
    }

    public List<DomainLoaderInfo> getDomainsList() {
        return domainsList;
    }

    public void setDomainsList(List<DomainLoaderInfo> domainsList) {
        this.domainsList = domainsList;
    }
}

