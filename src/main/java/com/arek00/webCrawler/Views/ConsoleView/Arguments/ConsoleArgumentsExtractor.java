package com.arek00.webCrawler.Views.ConsoleView.Arguments;


public class ConsoleArgumentsExtractor {

    public static ConsoleArguments extract(String[] arguments) throws IllegalConsoleArgumentException {
        ConsoleArguments.checkMandatoryParameters(arguments);
        return createConsoleArguments(arguments);
    }

    private static ConsoleArguments createConsoleArguments(String[] arguments) {
        ConsoleArguments consoleArguments = new ConsoleArguments();
        int argumentsCount = arguments.length;

        for (ConsoleParameter parameter : ConsoleParameter.values()) {
            for (int iteration = 0; iteration < argumentsCount; iteration++) {
                if (parameter.getValue().equals(arguments[iteration])) {
                    consoleArguments.setParameter(arguments[iteration], arguments[iteration + 1]);
                }
            }
        }
        return consoleArguments;
    }
}

