package com.arek00.webCrawler.Views.ConsoleView;


import com.arek00.webCrawler.Entities.Domains.Domain;
import com.arek00.webCrawler.Model.DownloadingStatistic;

import java.util.List;

public class ConsolePrinter {

    public void printWelcomeMessage() {
        print(ConsoleMessages.WELCOME_MESSAGE);
    }

    public void printMenu() {
        print(ConsoleMessages.MENU_MESSAGE);

        String[] options = new String[]{
                ConsoleMessages.START_CRAWLING_MENU_OPTION_MESSAGE,
                ConsoleMessages.SET_DIRECTORY_OPTION_MESSAGE,
                ConsoleMessages.ARTICLES_NUMBER_OPTION_MESSAGE,
                ConsoleMessages.LOAD_QUEUE_OPTION_MESSAGE,
                ConsoleMessages.LOAD_HISTORY_OPTION_MESSAGE,
                ConsoleMessages.EXIT_OPTION_MESSAGE
        };

        for (int option = 0; option < options.length; option++) {
            print("[" + option + "] - " + options[option]);
        }
    }

    public void printOnStartDownload(String source, int articlesNumber) {
        print(ConsoleMessages.ON_START_DOWNLOAD_MESSAGE);
        print(ConsoleMessages.DOWNLOAD_SOURCE_MESSAGE + source);

        String articlesNumberString = (articlesNumber > 0) ? Integer.toString(articlesNumber) : "infinite";

        print(ConsoleMessages.ARTICLES_NUMBER_MESSAGE + articlesNumberString);
        printLine();
    }

    public void printStatistics(DownloadingStatistic statistic) {
        int inQueue = statistic.getQueueLength();
        int downloaded = statistic.getDownloadedFiles();
        int visited = statistic.getVisitedLinks();

        print(ConsoleMessages.STATISTIC_MESSAGE +
                ConsoleMessages.VISITED_LINKS_STATISTIC_MESSAGE + visited +
                ConsoleMessages.DOWNLOADED_ARTICLES_STATISTIC_MESSAGE + downloaded +
                ConsoleMessages.LINKS_IN_QUEUE_STATISTIC_MESSAGE + inQueue);
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void printLine() {
        System.out.println("");
    }

    public void printDomains(List<Domain> domainsList) {
        int[] counter = {0};

        domainsList.forEach(domain -> {
            print("[" + counter[0] + "] - " + domain.getDomainName());
            counter[0]++;
        });
    }


    public void printArgumentsHelp() {
        String helpText = String
                .format("--Web Crawler--\n" +
                                "Arguments help:\n" +
                                "Mandatory: " + ConsoleArgumentsEnum.SAVE_DIRECTORY.getValue() + " directory to save files\n" +
                                "Mandatory: " + ConsoleArgumentsEnum.DOMAIN.getValue() + " index of available domain to download from\n" +
                                "Optional: " + ConsoleArgumentsEnum.MAX_ARTICLES.getValue() + " number of maximum articles to download\n" +
                                "Default is negative, which means articles downloading is limited by queue\n" +
                                "Optional: " + ConsoleArgumentsEnum.QUEUE_FILE + " file contains queue history. Queue will be also saved here\n" +
                                "Optional: " + ConsoleArgumentsEnum.VISITED_LINKS + " file contains visited links history. Visited links will be also saved there"
                );
        print(helpText);

    }
}
