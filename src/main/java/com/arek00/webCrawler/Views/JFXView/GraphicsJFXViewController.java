package com.arek00.webCrawler.Views.JFXView;

import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Model.DownloadingStatistic;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.Views.IView;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

/**
 */
public class GraphicsJFXViewController implements IView {
    private ViewController viewController;
    private SimpleStringProperty visitedLinksProperty;
    private SimpleStringProperty downloadedLinksProperty;
    private SimpleStringProperty linksInQueueProperty;
    private SimpleStringProperty rejectedLinksProperty;

    private IListener onStartedDownloadListener;
    private IListener onStoppedDownloadListener;
    private IListener onPausedDownloadListener;


    public GraphicsJFXViewController(ViewController jfxViewController) {
        ObjectValidator.nullPointerValidate(jfxViewController);
        this.viewController = jfxViewController;
        initializeListeners();
        bindValuesWithViewController();
    }

    private void bindValuesWithViewController() {
        visitedLinksProperty = new SimpleStringProperty("0");
        downloadedLinksProperty = new SimpleStringProperty("0");
        linksInQueueProperty = new SimpleStringProperty("0");

        viewController.bindDownloadedLinksNumber(downloadedLinksProperty);
        viewController.bindVisitedLinksNumber(visitedLinksProperty);
        viewController.bindLinksInQueueNumber(linksInQueueProperty);
    }

    private void initializeListeners() {
        onStartedDownloadListener = new OnStartDownload();
        onStoppedDownloadListener = new OnStopDownload();
    }


    public Domain getDomain() {
        return viewController.getDomain();
    }

    public void setDomainsList(List<Domain> domains) {
        ObjectValidator.nullPointerValidate(domains);
        viewController.setDomainsList(domains);
    }

    public String getQueuePath() {
        return viewController.queueFilePathField.getText();
    }

    public String getQueueSerializePath() {
        return viewController.queueSavePathTextField.getText();
    }

    public void setQueueSerializeListener(IListener listener) {
        viewController.setSerializeQueueListener(listener);
    }

    public String getDirectoryToDownloadPath() {
        return viewController.articlesDirectoryPathField.getText();
    }

    public String getVisitedLinksPath() {
        return viewController.visitedLinksPathField.getText();
    }

    public String getVisitedLinksSerializePath() {
        return viewController.visitedLinksSavePathField.getText();
    }

    public void setVisitedLinksSerializeListener(IListener listener) {
        viewController.setVisitedLinksSerializeListener(listener);

    }

    public String getArticlesNumber() {
        return viewController.articlesNumberPathField.getText();
    }

    public void showError(String errorMessage) {
        viewController.showError(errorMessage);
    }

    public void showMessage(String message) {
        viewController.setMessage(message);
    }

    public void setOnStartDownloadingListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);
        viewController.setOnStartDownloading(new ButtonAction(listener));
    }

    public void setOnStopDownloadingListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        viewController.setOnStopDownloadingListener(new ButtonAction(listener));
    }

    public void setOnPauseDownloadingListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);

        viewController.setOnPauseDownloading(new ButtonAction(listener));
    }

    public IListener getOnStartDownloadingListener() {
        return onStartedDownloadListener;
    }

    public IListener getOnStopDownloadingListener() {
        return onStoppedDownloadListener;
    }

    public IListener getOnPauseDownloadingListener() {
        return onPausedDownloadListener;
    }

    public void setDownloadStatistics(DownloadingStatistic statistics) {
        String visitedLinks = Integer.toString(statistics.getVisitedLinks());
        String downloadedLinks = Integer.toString(statistics.getDownloadedFiles());
        String linksInQueue = Integer.toString(statistics.getQueueLength());

        Platform.runLater(() -> {
            visitedLinksProperty.set(visitedLinks);
            downloadedLinksProperty.set(downloadedLinks);
            linksInQueueProperty.set(linksInQueue);
        });
    }


    class ButtonAction implements EventHandler<ActionEvent> {
        IListener listener;

        public ButtonAction(IListener action) {
            listener = action;
        }

        public void handle(ActionEvent event) {
            listener.inform();
        }
    }

    class OnStartDownload implements IListener {
        public void inform() {
            viewController.onStartDownload();
        }
    }

    class OnStopDownload implements IListener {

        public void inform() {
            viewController.onStopDownload();
        }
    }


    class OnPausedDownload implements IListener {

        public void inform() {
            viewController.onPauseDownload();
        }
    }
}
