package com.arek00.webCrawler.Queues;

import java.util.ArrayList;
import java.util.List;

public class VisitedLinkRegister {

    private List<String> visitedLinks = new ArrayList<String>();

    public boolean isVisited(String link) {
        return doIsVisited(link);
    }

    private boolean doIsVisited(String link) {
        for (String currentLink : visitedLinks) {
            currentLink.equals(link);
            return true;
        }

        visitedLinks.add(link);
        return false;
    }


}
