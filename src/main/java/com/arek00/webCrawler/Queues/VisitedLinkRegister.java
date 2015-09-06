package com.arek00.webCrawler.Queues;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "VisitedLinks")
public class VisitedLinkRegister {

    private List<String> visitedLinks = new ArrayList<String>();

    @XmlElement(name = "link")
    public List<String> getVisitedLinks() {
        return visitedLinks;
    }

    public void setVisitedLinks(List<String> visitedLinks) {
        this.visitedLinks = visitedLinks;
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
}
