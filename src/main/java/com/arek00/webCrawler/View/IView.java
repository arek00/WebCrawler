package com.arek00.webCrawler.View;


import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Model.DownloadingStatistic;
import com.arek00.webCrawler.Observers.IListener;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

/**
 */
public interface IView {

    public Domain getDomain();

    public void setDomainsList(List<Domain> domains);

    public String getQueuePath();

    public String getDirectoryToDownloadPath();

    public String getVisitedLinksPath();

    public String getArticlesNumber();

    public void showError(String errorMessage);

    public void showMessage(String messsage);

    public void setDownloadStatistics(DownloadingStatistic statistics);

    public void setOnStartDownloadingListener(IListener listener);

    public void setOnStopDownloadingListener(IListener listener);

    public void setOnPauseDownloadingListener(IListener listener);

    public IListener getOnStartDownloadingListener();

    public IListener getOnStopDownloadingListener();

    public IListener getOnPauseDownloadingListener();
}
