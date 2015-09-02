package com.arek00.webCrawler.Queues;


import com.arek00.webCrawler.Validators.ObjectValidator;

import java.util.*;

public class SimpleLinksQueue implements IQueue {

    private List<String> linksQueue = new LinkedList<String>();

    public void add(String link) {
        ObjectValidator.nullPointerValidate(link);

        linksQueue.add(link);
    }

    public void add(Collection<? extends String> links) {
        linksQueue.addAll(links);
    }

    public boolean hasNext() {
        return linksQueue.size() > 0;
    }

    public String next() {
        return linksQueue.remove(0);
    }
}
