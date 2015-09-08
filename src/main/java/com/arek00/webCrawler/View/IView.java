package com.arek00.webCrawler.View;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 */
public interface IView {

    public String getDomain();

    public String getSerializedQueuePath();

    public String getSerializedArticlesExtractorPath();

    public String getArticlesDirectory();

    public String getSerializedVisitedLinksPath();

    public String getArticlesNumber();

    public void showError(String errorMessage);

    public void setOnStartDownloadingListener(EventHandler<ActionEvent> listener);
}
