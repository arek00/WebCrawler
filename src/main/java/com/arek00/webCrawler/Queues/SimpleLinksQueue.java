package com.arek00.webCrawler.Queues;


import com.arek00.webCrawler.Validators.ObjectValidator;

import java.util.*;

public class SimpleLinksQueue implements IQueue {

    private List<String> linksQueue = new LinkedList<String>();
    private VisitedLinkRegister visitedLinks = null;
    private int visitedLinksNumber = 0;

    /**
     *
     * @param register Register that stores links visited in the past
     */
    public SimpleLinksQueue(VisitedLinkRegister register) {
        ObjectValidator.nullPointerValidate(register);

        this.visitedLinks = register;
    }

    public void add(String link) {
        ObjectValidator.nullPointerValidate(link);

        if (!visitedLinks.isVisited(link)) {
            visitedLinksNumber++;
            linksQueue.add(link);
        }
    }

    public void add(Collection<? extends String> links) {
        ObjectValidator.nullPointerValidate(links);

        for (String link : links) {
            add(link);
        }
    }

    public boolean hasNext() {
        return linksQueue.size() > 0;
    }

    public String next() {
        return linksQueue.remove(0);
    }

    public int size() {
        return linksQueue.size();
    }

    public int visitedLinks() {
        return visitedLinksNumber;
    }
}
