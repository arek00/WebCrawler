package com.arek00.webCrawler.Presenter;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.View.IView;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


/**
 */
public class Presenter {
    private Model model;
    private IView view;

    public Presenter(Model model, IView view) {
        ObjectValidator.nullPointerValidate(model, view);

        this.model = model;
        this.view = view;

        view.setOnStartDownloadingListener(new OnStartDownloading());
        model.setStatisticValuesListener(new OnStatisticValuesChanged());
    }


    class OnStartDownloading implements IListener {
        public void inform() {
            try {
                model.setDomain(view.getDomain());
                model.createArticlesExtractor(view.getSerializedArticlesExtractorPath());
                model.restoreQueue(view.getSerializedQueuePath());
                model.restoreVisitedLinks(view.getSerializedVisitedLinksPath());
                model.setArticlesDownloadPath(view.getArticlesDirectory());
                model.setArticlesNumber(Integer.parseInt(view.getArticlesNumber()));
                model.startDownloadingArticles();
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

    class OnStatisticValuesChanged implements IListener {

        SimpleStringProperty downloadedArticlesBind;
        SimpleStringProperty visitedLinksBind;
        SimpleStringProperty linksInQueueBind;

        public OnStatisticValuesChanged() {

            downloadedArticlesBind = new SimpleStringProperty("0");
            visitedLinksBind = new SimpleStringProperty("0");
            linksInQueueBind = new SimpleStringProperty("0");

            view.bindDownloadedLinksNumber(downloadedArticlesBind);
            view.bindVisitedLinksNumber(visitedLinksBind);
            view.bindLinksInQueueNumber(linksInQueueBind);
        }

        public void inform() {

            Platform.runLater(new Runnable() {
                public void run() {

                    String downloadedArticles = Integer.toString(model.getDownloadedArticles());
                    String visitedLinks = Integer.toString(model.getVisitedLinks());
                    String linksInQueue = Integer.toString(model.getLinksInQueue());

                    downloadedArticlesBind.set(downloadedArticles);
                    visitedLinksBind.set(visitedLinks);
                    linksInQueueBind.set(linksInQueue);
                }
            });
        }
    }


}
