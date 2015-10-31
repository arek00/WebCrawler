package com.arek00.webCrawler.Views.ConsoleView;

import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Model.DownloadingStatistic;
import com.arek00.webCrawler.Observers.IListener;
import com.arek00.webCrawler.Validators.ObjectValidator;
import com.arek00.webCrawler.Views.IView;

import java.util.List;


public class ConsoleView implements IView {

    private List<Domain> domainsList;
    private int domainIndex = 0;
    private String queuePath = "";
    private String visitedLinksPath = "";
    private String downloadPath = "";
    private int maxArticles = -1;


    private IListener onStartDownloadingListener;
    private IListener onStopDownloadingListener;
    private IListener onPauseDownloadingListener;

    private ConsolePrinter printer = new ConsolePrinter();
    private ConsoleMenu menu;

    public ConsoleView() {
        menu = new ConsoleMenu();
    }

    public void show() {
        menu.mainMenu();
    }

    public ConsoleView(String[] args) {

    }


    @Override
    public Domain getDomain() {
        if (domainsList == null) {
            showError("Domains haven't been set");
        }
        return domainsList.get(domainIndex);
    }

    @Override
    public void setDomainsList(List<Domain> domains) {
        ObjectValidator.nullPointerValidate(domains);
        this.domainsList = domains;
    }

    @Override
    public String getQueuePath() {
        return this.queuePath;
    }

    @Override
    public String getQueueSerializePath() {
        return null;
    }

    @Override
    public void setQueueSerializeListener(IListener listener) {
    }

    @Override
    public String getDirectoryToDownloadPath() {
        return this.downloadPath;
    }

    @Override
    public String getVisitedLinksPath() {
        return this.visitedLinksPath;
    }

    @Override
    public String getVisitedLinksSerializePath() {
        return null;
    }

    @Override
    public void setVisitedLinksSerializeListener(IListener listener) {
    }

    @Override
    public String getArticlesNumber() {
        return Integer.toString(this.maxArticles);
    }

    @Override
    public void showError(String errorMessage) {
        printer.print("ERROR: " + errorMessage);
    }

    @Override
    public void showMessage(String message) {
        printer.print("MESSAGE: " + message);
    }

    @Override
    public void setDownloadStatistics(DownloadingStatistic statistics) {
        printer.printStatistics(statistics);
    }

    @Override
    public void setOnStartDownloadingListener(IListener listener) {
        this.onStartDownloadingListener = listener;
    }

    @Override
    public void setOnStopDownloadingListener(IListener listener) {
        this.onStopDownloadingListener = listener;
    }

    @Override
    public void setOnPauseDownloadingListener(IListener listener) {
    }

    @Override
    public IListener getOnStartDownloadingListener() {
        return () -> {
            printer.printOnStartDownload(getDomain().getDomainName(), this.maxArticles);
        };
    }

    @Override
    public IListener getOnStopDownloadingListener() {
        return () -> {
            printer.print(ConsoleMessages.STOP_DOWNLOAD_MESSAGE);
            menu.mainMenu();
        };
    }

    @Override
    public IListener getOnPauseDownloadingListener() {
        return null;
    }


    /**
     * Management of inputs and printing
     */
    class ConsoleMenu {

        private ConsolePrinter printer;
        private ConsoleInput input;

        public ConsoleMenu() {
            printer = new ConsolePrinter();
            input = new ConsoleInput();
        }

        protected void mainMenu() {
            printer.printWelcomeMessage();
            printer.printMenu();
            mainMenuAction(input.getAction());
        }

        private void mainMenuAction(String action) {
            Options option = Options.getOptionByValue(-1);

            try {
                option = Options.getOptionByValue(Integer.parseInt(action));
            } catch (NumberFormatException exception) {
                showError(ConsoleMessages.INVALID_OPTION_TYPE_MESSAGE);
                mainMenu();
                return;
            }

            if (option == Options.UNKNOWN) {
                showError(ConsoleMessages.INVALID_OPTION_NUMBER_MESSAGE);
                mainMenu();
                return;
            }

            doMainMenuAction(option);
        }

        private void doMainMenuAction(Options option) {
            switch (option) {
                case START_DOWNLOAD_OPTION:
                    startDownloadMenu();
                    break;
                case SET_DOWNLOAD_DIRECTORY:
                    setDownloadPathMenu();
                    break;
                case SET_QUEUE:
                    setQueueMenu();
                    break;
                case SET_VISITED_LINKS:
                    setVisitedLinksMenu();
                    break;
                case EXIT_OPTION:
                    exit();
                    break;
                default:
                    showError(ConsoleMessages.INVALID_OPTION_NUMBER_MESSAGE);
                    mainMenu();
                    break;
            }
        }

        private void startDownloadMenu() {
            onStartDownloadingListener.inform();
        }

        private void setDownloadPathMenu() {
            printer.print(ConsoleMessages.SET_DOWNLOAD_PATH_MESSAGE);
            printer.print(ConsoleMessages.CURRENT_DOWNLOAD_PATH_MESSAGE + downloadPath);
            String action = input.getAction();
            ConsoleActionsValidator.validateDirectoryPath(action);

            downloadPath = action;
            mainMenu();
        }

        private void setQueueMenu() {
            printer.print(ConsoleMessages.SET_QUEUE_FILE_MESSAGE);
            printer.print(ConsoleMessages.CURRENT_QUEUE_FILE_MESSAGE + queuePath);
            String action = input.getAction();

            ConsoleActionsValidator.validateFilePath(action);
            queuePath = action;
            mainMenu();
        }

        private void setVisitedLinksMenu() {
            printer.print(ConsoleMessages.SET_VISITED_LINKS_FILE_MESSAGE);
            printer.print(ConsoleMessages.CURRENT_VISITED_LINKS_FILE_MESSAGE + visitedLinksPath);
            String action = input.getAction();

            ConsoleActionsValidator.validateFilePath(action);
            visitedLinksPath = action;
            mainMenu();
        }

        private void exit() {
            printer.print(ConsoleMessages.EXIT_OPTION_MESSAGE);
        }
    }


}
