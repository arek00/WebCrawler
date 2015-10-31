package com.arek00.webCrawler;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Presenter.Presenter;
import com.arek00.webCrawler.Views.JFXView.GraphicsJFXViewController;
import com.arek00.webCrawler.Views.IView;
import com.arek00.webCrawler.Views.JFXView.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class WindowModeMain extends Application {

    public static void main(String[] args) {

        launch(WindowModeMain.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/view.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);

        ViewController viewController = loader.getController();
        viewController.setUserWindow(primaryStage);

        IView view = new GraphicsJFXViewController(viewController);
        Model model = new Model();
        Presenter presenter = new Presenter(model, view);

        primaryStage.show();
    }

    private void setViewController()
    {

    }

}
