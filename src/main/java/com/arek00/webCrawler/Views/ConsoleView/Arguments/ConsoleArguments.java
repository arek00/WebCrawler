package com.arek00.webCrawler.Views.ConsoleView.Arguments;

import lombok.Getter;
import lombok.NonNull;

/**
 * Value object that contains parsed arguments from console.
 *
 */
public class ConsoleArguments {

    @Getter private int maxArticles = -1;
    @Getter private String downloadPath = "";
    @Getter private String queuePath = "";
    @Getter private String visitedLinksPath = "";
    @Getter private int domainIndex = 0;

    public void setParameter(@NonNull String prefix, @NonNull String parameter) {
        ConsoleParameter argument = ConsoleParameter.getArgumentByPrefix(prefix);
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

    public static void checkMandatoryParameters(String[] arguments) throws IllegalConsoleArgumentException {
        for (ConsoleParameter parameter : ConsoleParameter.values()) {
            if (parameter.isMandatory()) {
                if (!argumentsContainPrefix(parameter.getValue(), arguments)) {
                    throw new IllegalConsoleArgumentException("Arguments don't contain mandatory parameter: " + parameter.getValue());
                }
            }
        }
    }

    private static boolean argumentsContainPrefix(String prefix, String[] arguments) {
        for (String argument : arguments) {
            if (argument.equals(prefix)) {
                return true;
            }
        }

        return false;
    }
}
