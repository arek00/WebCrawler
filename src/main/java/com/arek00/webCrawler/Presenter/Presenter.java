package com.arek00.webCrawler.Presenter;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.View.IView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


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
    }


    class OnStartDownloading implements EventHandler<ActionEvent> {

        public void handle(ActionEvent event) {
            try {

                System.out.println("On Start Downloading Clicked");
                model.setDomain(view.getDomain());
                model.createArticlesExtractor(view.getSerializedArticlesExtractorPath());
                model.restoreQueue(view.getSerializedQueuePath());
                model.restoreVisitedLinks(view.getSerializedVisitedLinksPath());
                model.startDownloadingArticles();
            } catch (Exception exception) {
                exception.printStackTrace();
                view.showError(exception.getMessage());
            }
        }
    }

}
