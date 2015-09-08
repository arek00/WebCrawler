package com.arek00.webCrawler;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Presenter.Presenter;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.View.IView;
import com.arek00.webCrawler.View.ViewController;
import com.arek00.webCrawler.View.ViewInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class WindowModeMain extends Application {

    public static void main(String[] args) {

        launch(WindowModeMain.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);

        ViewController viewController = loader.getController();

        Model model = new Model();
        Presenter presenter = new Presenter(model, viewController);

        primaryStage.show();
    }

    private void setViewController()
    {

    }

}
