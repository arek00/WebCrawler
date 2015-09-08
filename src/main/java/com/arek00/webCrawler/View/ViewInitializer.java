package com.arek00.webCrawler.View;

import com.arek00.webCrawler.Validators.ObjectValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 */
public class ViewInitializer extends Application {

    private IView viewController;
    private Stage primaryStage;

    public static void initializeView(String[] args) {
        System.out.println("Launching view");

        launch(ViewInitializer.class, args);
        System.out.println("Launched view");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        this.viewController = loader.getController();
    }

    public IView getViewController() {
        return this.viewController;
    }

    public void showView() {
        this.primaryStage.show();
    }

}
