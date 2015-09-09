package com.arek00.webCrawler.View;


import com.arek00.webCrawler.Observers.IListener;
import javafx.beans.property.StringProperty;
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

    public void setOnStartDownloadingListener(IListener listener);

    public void bindVisitedLinksNumber(StringProperty property);

    public void bindDownloadedLinksNumber(StringProperty property);

    public void bindLinksInQueueNumber(StringProperty property);
}
