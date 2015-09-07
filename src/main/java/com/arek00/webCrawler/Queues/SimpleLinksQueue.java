package com.arek00.webCrawler.Queues;


import com.arek00.webCrawler.Validators.ObjectValidator;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.*;

@Root
public class SimpleLinksQueue implements IQueue {

    @ElementList(name = "queue")
    private List<String> linksQueue = new LinkedList<String>();

    @Attribute private String domain;

    private VisitedLinkRegister visitedLinks = null;

    private int visitedLinksNumber = 0;

    /**
     * @param register Register that stores links visited in the past
     */
    public SimpleLinksQueue(VisitedLinkRegister register, String domain) {
        ObjectValidator.nullPointerValidate(register, domain);

        this.domain = domain;
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

    /**
     * Framework mandatory implementation
     */

    public SimpleLinksQueue() {
    }

    public List<String> getLinksQueue() {
        return linksQueue;
    }

    public void setLinksQueue(List<String> linksQueue) {
        this.linksQueue = linksQueue;
        this.visitedLinksNumber = linksQueue.size();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
