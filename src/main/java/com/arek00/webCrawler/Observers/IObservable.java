package com.arek00.webCrawler.Observers;

/**
 */
public interface IObservable {

    public void addListener(IListener listener);
    public void removeListener(IListener listener);
    public void informListeners();

}
