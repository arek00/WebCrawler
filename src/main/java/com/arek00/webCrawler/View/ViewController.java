package com.arek00.webCrawler.View;

import com.arek00.webCrawler.Validators.ObjectValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;


/**
 * Controller of view.fxml
 */
public class ViewController implements IView {
    public Button startDownloadingButton;
    public TextField pageDomainField;
    public TextField articlesDirectoryPathField;
    public TextField articlesExtractorFilePathField;
    public TextField queueFilePathField;
    public TextField visitedLinksPathField;
    public TextField articlesNumberPathField;
    public Button extractorFileButton;
    public Button queueFileButton;
    public Button visitedLinksFileButton;
    public Button articlesDirectoryButton;
    public Pane mainPane;

    private EventHandler<ActionEvent> onStartDownloadingHandler;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Window userWindow;

    public String getDomain() {
        return pageDomainField.getText();
    }

    public String getSerializedQueuePath() {
        return queueFilePathField.getText();
    }

    public String getSerializedArticlesExtractorPath() {
        return articlesExtractorFilePathField.getText();
    }

    public String getArticlesDirectory() {
        return articlesDirectoryPathField.getText();
    }

    public String getSerializedVisitedLinksPath() {
        return visitedLinksPathField.getText();
    }

    public String getArticlesNumber() {
        return articlesNumberPathField.getText();
    }

    public void showError(String errorMessage) {

    }

    public void setOnStartDownloadingListener(EventHandler<ActionEvent> listener) {
        ObjectValidator.nullPointerValidate(listener);

        this.onStartDownloadingHandler = listener;
        startDownloadingButton.setOnAction(listener);
    }

    public void onStartDownloading(ActionEvent actionEvent) {
        onStartDownloadingHandler.handle(actionEvent);
    }


    public void onChooseArticlesDirectory(ActionEvent actionEvent) {
        File directory = directoryChooser.showDialog(userWindow);
        articlesDirectoryPathField.setText(directory.getAbsolutePath());
    }

    public void onChooseExtractorFile(ActionEvent actionEvent) {
        File directory = fileChooser.showOpenDialog(userWindow);
        articlesExtractorFilePathField.setText(directory.getAbsolutePath());
    }

    public void onChooseQueueHistoryFile(ActionEvent actionEvent) {
        File directory = fileChooser.showOpenDialog(userWindow);
        queueFilePathField.setText(directory.getAbsolutePath());
    }

    public void onChooseVisitedLinksFile(ActionEvent actionEvent) {
        File directory = fileChooser.showOpenDialog(userWindow);
        visitedLinksPathField.setText(directory.getAbsolutePath());
    }

    public void setUserWindow(Window window) {
        this.userWindow = window;

        this.fileChooser = new FileChooser();
        this.directoryChooser = new DirectoryChooser();
    }
}
