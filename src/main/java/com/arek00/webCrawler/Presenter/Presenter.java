package com.arek00.webCrawler.Presenter;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.Views.IView;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;


/**
 */
public class Presenter {
    private Model model;
    private IView view;

    public Presenter(Model model, IView view) {
        ObjectValidator.nullPointerValidate(model, view);

        this.model = model;
        this.view = view;
        setListeners();

        view.show();
    }

    private void setListeners() {
        view.setOnStartDownloadingListener(new OnStartDownloading());
        view.setOnStopDownloadingListener(new OnStopDownloading());
        view.setDomainsList(model.getDomains());
        view.setQueueSerializeListener(new SaveQueueListener());
        view.setVisitedLinksSerializeListener(new SaveVisitedLinksListener());

        model.setMessageListener(new MessageListener());
        model.setOnStartDownloadListener(view.getOnStartDownloadingListener());
        model.setOnStopDownloadListener(view.getOnStopDownloadingListener());
        model.setStatisticUpdateListener(new OnDownloadingStatisticChanged());
    }


    class MessageListener implements IListener {

        public void inform() {
            view.showMessage(model.getMessage());
        }
    }

    class OnStartDownloading implements IListener {
        public void inform() {
            try {
                model.setDomain(view.getDomain());
                model.loadQueueFile(view.getQueuePath());
                model.loadVisitedLinks(view.getVisitedLinksPath());
                model.setArticlesDownloadPath(view.getDirectoryToDownloadPath());
                model.setArticlesNumber(Integer.parseInt(view.getArticlesNumber()));
                model.startDownloading();
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
                String message = String.format("%s is incorrect articles number. Please set numeric value", view.getArticlesNumber());
                view.showError(message);
            } catch (IllegalArgumentException exception) {
                exception.printStackTrace();
                view.showError(exception.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
                view.showError(exception.getMessage());
            }
        }
    }

    class OnStopDownloading implements IListener {

        public void inform() {
            model.stopDownloading();
        }
    }

    class OnDownloadingStatisticChanged implements IListener {

        SimpleStringProperty downloadedArticlesBind;
        SimpleStringProperty visitedLinksBind;
        SimpleStringProperty linksInQueueBind;

        public OnDownloadingStatisticChanged() {

            downloadedArticlesBind = new SimpleStringProperty("0");
            visitedLinksBind = new SimpleStringProperty("0");
            linksInQueueBind = new SimpleStringProperty("0");
        }

        public void inform() {
            view.setDownloadStatistics(model.getDownloadingStatistics());
        }
    }

    class SaveQueueListener implements IListener {

        public void inform() {
            try {
                model.serializeQueue(view.getQueueSerializePath());
            } catch (Exception e) {
                e.printStackTrace();
                view.showError("Problem with serialize queue: " + e.getMessage());
            }
        }
    }


    class SaveVisitedLinksListener implements IListener {

        public void inform() {
            try {
                model.serializeVisitedLinks(view.getVisitedLinksSerializePath());
            } catch (Exception e) {
                e.printStackTrace();
                view.showError("Problem with serialize visited links: " + e.getMessage());
            }
        }
    }


}
