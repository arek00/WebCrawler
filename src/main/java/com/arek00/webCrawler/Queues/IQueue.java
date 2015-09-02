package com.arek00.webCrawler.Queues;

import java.util.Collection;
import java.util.Iterator;


public interface IQueue extends Iterator<String> {
    public void add(String link);

    public void add(Collection<? extends String> links);

    public boolean hasNext();

    public String next();
}
