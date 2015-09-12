package com.arek00.webCrawler.View;

import com.arek00.webCrawler.Entities.Domains.Domain;
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
    public Label visitedLinksNumberLabel;
    public Label downloadedLinksNumberLabel;
    public Label linksInQueueNumberLabel;
    public ChoiceBox domainChoiceBox;

    private EventHandler<ActionEvent> onStartDownloadingHandler;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private Window userWindow;

    private List<IListener> onStartDownloadingListeners = new ArrayList<IListener>();

    public Domain getDomain() {
        return (Domain) domainChoiceBox.getValue();
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public void setOnStartDownloadingListener(IListener listener) {
        ObjectValidator.nullPointerValidate(listener);
        this.onStartDownloadingListeners.add(listener);
    }

    public void onStartDownloading(ActionEvent actionEvent) {
        for (IListener listener : onStartDownloadingListeners) {
            listener.inform();
        }
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
}
