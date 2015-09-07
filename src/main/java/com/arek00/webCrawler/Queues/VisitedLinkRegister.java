package com.arek00.webCrawler.Queues;

import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "VisitedLinks")
public class VisitedLinkRegister {

    @ElementList(name = "link")
    private List<String> visitedLinks = new ArrayList<String>();
    @Attribute(name = "domain")
    private String domain;

    public VisitedLinkRegister(String domain) {
        ObjectValidator.nullPointerValidate(domain);

        this.domain = domain;
    }


    public boolean isVisited(String link) {
        return doIsVisited(link);
    }

    private boolean doIsVisited(String link) {
        for (String currentLink : visitedLinks) {
            if (currentLink.equals(link)) {
                return true;
            }
        }

        visitedLinks.add(link);
        return false;
    }

    /**
     * Framework mandatory implementation

     */

    public VisitedLinkRegister() {
    }

    public List<String> getVisitedLinks() {
        return visitedLinks;
    }

    public void setVisitedLinks(List<String> visitedLinks) {
        this.visitedLinks = visitedLinks;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
