package com.arek00.webCrawler.View;

import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Model.DownloadingStatistic;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Validators.ObjectValidator;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Controller of view.fxml
 */
public class ViewController {
    public Button startDownloadingButton;
    public TextField articlesDirectoryPathField;
    public TextField queueFilePathField;
    public TextField visitedLinksPathField;
    public TextField articlesNumberPathField;
    public Button queueFileButton;
    public Button visitedLinksFileButton;
    public Button articlesDirectoryButton;
    public Pane mainPane;
    public Label visitedLinksNumberLabel;
    public Label downloadedLinksNumberLabel;
    public Label linksInQueueNumberLabel;
    public ChoiceBox domainChoiceBox;
    public Pane downloadingPane;
    public Pane settingsPane;
    public Button pauseDownloadingButton;
    public Button stopDownloadingButton;
    public TextArea messagesTextArea;

    private EventHandler<ActionEvent> onStartDownloadingHandler;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Window userWindow;
    private String messages = "";

    public Domain getDomain() {
        return (Domain) domainChoiceBox.getValue();
    }

    public void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void setMessage(String message) {
        if (this.messages.isEmpty()) {
            this.messages += message;
        } else {
            this.messages += '\n' + message;
        }

        messagesTextArea.setText(this.messages);
    }

    public void setOnStartDownloading(EventHandler<ActionEvent> action) {
        startDownloadingButton.setOnAction(action);
    }

    public void setOnPauseDownloading(EventHandler<ActionEvent> action) {
        pauseDownloadingButton.setOnAction(action);
    }

    public void setOnStopDownloadingListener(EventHandler<ActionEvent> action) {
        stopDownloadingButton.setOnAction(action);
    }

    public void onChooseArticlesDirectory(ActionEvent actionEvent) {
        File directory = directoryChooser.showDialog(userWindow);
        articlesDirectoryPathField.setText(directory.getAbsolutePath());
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

    public void bindVisitedLinksNumber(StringProperty property) {
        visitedLinksNumberLabel.textProperty().bind(property);
    }

    public void bindDownloadedLinksNumber(StringProperty property) {
        downloadedLinksNumberLabel.textProperty().bind(property);
    }

    public void bindLinksInQueueNumber(StringProperty property) {
        linksInQueueNumberLabel.textProperty().bind(property);
    }

    public void setDomainsList(List<Domain> domains) {
        ObservableList<Domain> choiceBoxElements = FXCollections.observableList(domains);
        domainChoiceBox.setItems(choiceBoxElements);
    }

    public void onStopDownload() {
        downloadingPane.setVisible(false);
        settingsPane.setVisible(true);

        startDownloadingButton.setDisable(false);
    }

    public void onStartDownload() {
        downloadingPane.setVisible(true);
        settingsPane.setVisible(false);
        startDownloadingButton.setDisable(true);
    }

    public void onPauseDownload() {


    }
}
