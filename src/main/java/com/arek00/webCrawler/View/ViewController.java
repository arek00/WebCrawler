package com.arek00.webCrawler.View;

import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Observers.IListener;
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
    public Pane serializationPane;
    public TextField queueSavePathTextField;
    public TextField visitedLinksSavePathField;
    public Button queueSaveButton;
    public Button visitedLinksSaveButton;
    public Button saveDownloadStateButton;
    public Button backToMenuButton;
    public Button clearMessagesButton;

    private EventHandler<ActionEvent> onStartDownloadingHandler;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Window userWindow;
    private String messages = "";
    private IListener serializeQueueListener;
    private IListener visitedLinksSerializeListener;


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
        backToMenuButton.setDisable(false);
        saveDownloadStateButton.setDisable(false);
        startDownloadingButton.setDisable(false);
    }

    public void onStartDownload() {
        backToMenuButton.setDisable(true);
        saveDownloadStateButton.setDisable(true);
        downloadingPane.setVisible(true);
        settingsPane.setVisible(false);
        startDownloadingButton.setDisable(true);
    }

    public void onPauseDownload() {


    }

    public void onSaveQueueClick(ActionEvent actionEvent) {
        File fileToSave = fileChooser.showSaveDialog(userWindow);

        if (fileToSave != null) {
            queueSavePathTextField.setText(fileToSave.getAbsolutePath());
            serializeQueueListener.inform();
        }
    }

    public void onSaveVisitedLinksClick(ActionEvent actionEvent) {
        File fileToSave = fileChooser.showSaveDialog(userWindow);

        if (fileToSave != null) {
            visitedLinksSavePathField.setText(fileToSave.getAbsolutePath());
            visitedLinksSerializeListener.inform();
        }
    }

    public void onBackToMenuButtonClick(ActionEvent actionEvent) {
        downloadingPane.setVisible(false);
        serializationPane.setVisible(false);
        settingsPane.setVisible(true);
    }

    public void setSerializeQueueListener(IListener serializeQueueListener) {
        this.serializeQueueListener = serializeQueueListener;
    }

    public void setVisitedLinksSerializeListener(IListener visitedLinksSerializeListener) {
        this.visitedLinksSerializeListener = visitedLinksSerializeListener;
    }

    public void onSaveDownloadStateClick(ActionEvent actionEvent) {
        downloadingPane.setVisible(false);
        settingsPane.setVisible(false);
        serializationPane.setVisible(true);
    }

    public void onClearMessagesClick(ActionEvent actionEvent) {
        messages = "";
        messagesTextArea.setText(messages);
    }
}
