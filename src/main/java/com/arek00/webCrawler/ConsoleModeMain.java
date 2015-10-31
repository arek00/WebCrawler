package com.arek00.webCrawler;

import com.arek00.webCrawler.Model.Model;
import com.arek00.webCrawler.Presenter.Presenter;
import com.arek00.webCrawler.Views.ConsoleView.ConsoleView;
import com.arek00.webCrawler.Views.IView;

/**
 * Run crawler in console mode.
 */
public class ConsoleModeMain {

    public static void main(String args[]) {
        ConsoleView view;

        if (args.length == 0) {
            view = new ConsoleView();
        } else {
            view = new ConsoleView(args);
        }

        Model model = new Model();
        Presenter presenter = new Presenter(model, view);
        view.show();
    }

}
