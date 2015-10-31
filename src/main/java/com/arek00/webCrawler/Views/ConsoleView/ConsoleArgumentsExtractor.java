package com.arek00.webCrawler.Views.ConsoleView;


import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class ConsoleArgumentsExtractor {

    private int maxArticles;
    private String downloadPath;
    private String queuePath;
    private String visitedLinksPath;
    private int domainIndex;

    private String[] arguments;

    public ConsoleArgumentsExtractor(String[] arguments) {
        this.arguments = arguments;
    }

    public ConsoleArguments extract(String[] arguments) {
        ConsoleArguments consoleArguments = new ConsoleArguments();
        int argumentsCount = arguments.length;

        for (ConsoleArgumentsEnum parameter : ConsoleArgumentsEnum.values()) {
            for (int iteration = 0; iteration < argumentsCount; iteration++) {
                if (parameter.getValue().equals(arguments[iteration])) {
                    consoleArguments.setParameter(arguments[iteration], arguments[iteration + 1]);
                }
            }
        }
        return consoleArguments;
    }


}

class ConsoleArguments {

    @Getter private int maxArticles = -1;
    @Getter private String downloadPath = "";
    @Getter private String queuePath = "";
    @Getter private String visitedLinksPath = "";
    @Getter private int domainIndex = 0;

    public void setParameter(@NonNull String prefix, @NonNull String parameter) {
        ConsoleArgumentsEnum argument = ConsoleArgumentsEnum.getArgumentByPrefix(prefix);
        switch (argument) {
            case DOMAIN:
                this.domainIndex = Integer.parseInt(parameter);
                break;
            case SAVE_DIRECTORY:
                this.downloadPath = parameter;
                break;
            case MAX_ARTICLES:
                this.maxArticles = Integer.parseInt(parameter);
                break;
            case QUEUE_FILE:
                this.queuePath = parameter;
                break;
            case VISITED_LINKS:
                this.visitedLinksPath = parameter;
                break;
            default:
                break;
        }
    }


}